package com.dongyang.seoulTravel.entity.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    private String user_email;
    private String friend_email; // 친구 이메일

    private FriendshipStatus status; // 수락, 대기 상태
    private boolean isFrom; // 요청 발신 위치
    private Integer counterpartId; // 상대 아이디

    public void acceptFriendshipRequest() {
        status = FriendshipStatus.ACCEPT;
    }

    public void setCounterpartId(Integer id) {
        counterpartId = id;
    }

    // 추가된 getter 메서드
    public String getFriendEmail() {
        return friend_email;
    }
}
