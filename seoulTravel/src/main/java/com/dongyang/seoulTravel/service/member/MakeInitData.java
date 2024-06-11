package com.dongyang.seoulTravel.service.member;

import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.entity.member.UserRole;
import com.dongyang.seoulTravel.repository.member.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MakeInitData {
    private final UserRepository userRepository;

    @PostConstruct
    public void makeAdminAndUser() {
        User admin1 = User.builder()
                .loginId("admin1")
                .password("1234")
                .nickname("관리자1")
                .role(UserRole.ADMIN)
                .build();
        userRepository.save(admin1);

        User user1 = User.builder()
                .loginId("user1")
                .password("1234")
                .nickname("User1")
                .role(UserRole.USER)
                .build();
        userRepository.save(user1);


    }
}
