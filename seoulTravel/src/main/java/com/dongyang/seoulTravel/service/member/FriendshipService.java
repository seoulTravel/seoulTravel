package com.dongyang.seoulTravel.service.member;

import com.dongyang.seoulTravel.entity.member.FriendshipStatus;
import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.entity.member.Friendship;

import com.dongyang.seoulTravel.repository.member.FriendshipRepository;
import com.dongyang.seoulTravel.repository.member.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;


    @Autowired
    private HttpSession session;  // HttpSession을 주입받아 사용

    public void createFriendship(String toEmail) throws Exception{

        // 현재 로그인 되어있는 사람 (보내는 사람)
        String fromEmail = (String) session.getAttribute("user_email");  // 세션에서 이메일을 가져옴

        if (fromEmail == null) {
            throw new Exception("로그인 정보가 없습니다.");
        }

        // 유저 정보를 모두 가져옴
        User fromUser = userRepository.findByEmail(fromEmail).orElseThrow(() -> new Exception("회원 조회 실패"));
        User toUser = userRepository.findByEmail(toEmail).orElseThrow(() -> new Exception("회원 조회 실패"));

        // 받는 사람 측에 저장될 친구 요청
        Friendship friendshipFrom = Friendship.builder()
                .user(fromUser)
                .user_email(fromEmail)
                .friend_email(toEmail)
                .status(FriendshipStatus.WAITING)
                .isFrom(true)
                .build();

        // 보내는 사람 쪽에 저장될 친구 요청
        Friendship friendshipTo = Friendship.builder()
                .user(toUser)
                .user_email(toEmail)
                .friend_email(fromEmail)
                .status(FriendshipStatus.WAITING)
                .isFrom(false)
                .build();

        // 각각의 유저 리스트에 저장
        fromUser.getFriendshipList().add(friendshipTo);
        toUser.getFriendshipList().add(friendshipFrom);

        // 저장을 먼저 하는 이유는, 그래야 서로의 친구요청 번호가 생성되기 때문이다.
        friendshipRepository.save(friendshipTo);
        friendshipRepository.save(friendshipFrom);

        // 매칭되는 친구 요청의 아이디를 서로 저장한다.
        friendshipTo.setCounterpartId(friendshipFrom.getId());
        friendshipFrom.setCounterpartId(friendshipTo.getId());
    }
    @Transactional
    public ResponseEntity<?> getWaitingFriendList(Object userEmail) throws Exception {
        // 현재 로그인한 유저의 정보를 불러온다
        User user = (User) session.getAttribute("user_email");
        List<Friendship> friendshipList = user.getFriendshipList();
        // 조회된 결과 객체를 담을 Dto 리스트
        List<WaitingFriendListDto> result = new ArrayList<>();

        for (Friendship x : friendshipList) {
            // 보낸 요청이 아니고 && 수락 대기중인 요청만 조회
            if (!x.isFrom() && x.getStatus() == FriendshipStatus.WAITING) {
                User friend = userRepository.findByEmail(x.getFriendEmail()).orElseThrow(() -> new Exception("회원 조회 실패"));
                WaitingFriendListDto dto = WaitingFriendListDto.builder()
                        .friendshipId(x.getId())
                        .friendEmail(friend.getUser_email())
                        .friendName(friend.getUser_name())
                        .status(x.getStatus())
                        .build();
                result.add(dto);
            }
        }
        // 결과 반환
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    public String approveFriendshipRequest(Integer friendshipId) throws Exception {
        // 누를 친구 요청과 매칭되는 상대방 친구 요청 둘다 가져옴
        Friendship friendship = friendshipRepository.findById(friendshipId).orElseThrow(() -> new Exception("친구 요청 조회 실패"));
        Friendship counterFriendship = friendshipRepository.findById(friendship.getCounterpartId()).orElseThrow(() -> new Exception("친구 요청 조회 실패"));

        // 둘다 상태를 ACCEPT로 변경함
        friendship.acceptFriendshipRequest();
        counterFriendship.acceptFriendshipRequest();

        return "승인 성공";
    }
    @Data
    @Builder
    static class WaitingFriendListDto {
        private Integer friendshipId;
        private String friendEmail;
        private String friendName;
        private FriendshipStatus status;

    }
}
