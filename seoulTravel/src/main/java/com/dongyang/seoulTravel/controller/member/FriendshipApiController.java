package com.dongyang.seoulTravel.controller.member;

import com.dongyang.seoulTravel.service.member.FriendshipService;
import com.dongyang.seoulTravel.service.member.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // RestController 어노테이션 추가
public class FriendshipApiController {

    @Autowired
    private UserService userService;  // 인스턴스 변수로 주입된 userService 사용
    @Autowired
    private HttpSession session;  // HttpSession을 주입받아 사용
    @Autowired
    private FriendshipService friendshipService;  // FriendshipService도 주입받는다고 가정

    @PostMapping("/user/friends/{email}")
    @ResponseStatus(HttpStatus.OK)
    public String sendFriendshipRequest(@Valid @PathVariable("email") String email) throws Exception {
        // 인스턴스 메서드로 userService 사용
        if(!userService.isExistByEmail(email)) {
            throw new Exception("대상 회원이 존재하지 않습니다");
        }
        //friendshipService.createFriendship(email);
        return "친구추가 성공";
    }
    @GetMapping("/user/friends/received")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getWaitingFriendInfo() throws Exception {
        return friendshipService.getWaitingFriendList(session.getAttribute("user_email"));
    }
    @PostMapping("/user/friends/approve/{friendshipId}")
    @ResponseStatus(HttpStatus.OK)
    public String approveFriendship (@Valid @PathVariable("friendshipId") Integer friendshipId) throws Exception{
        return friendshipService.approveFriendshipRequest(friendshipId);
    }
}
