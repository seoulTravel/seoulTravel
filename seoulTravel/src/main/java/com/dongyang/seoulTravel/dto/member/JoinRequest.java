package com.dongyang.seoulTravel.dto.member;

import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.entity.member.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 디폴드 생성자
public class JoinRequest {
    private String loginId;
    private String password;
    private String passwordCheck;
    private String nickname;

    public User toEntity(){
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .nickname(this.nickname)
                .role(UserRole.USER)
                .build();
    }

}
