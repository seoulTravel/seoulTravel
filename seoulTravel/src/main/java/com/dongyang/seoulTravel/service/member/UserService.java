package com.dongyang.seoulTravel.service.member;

import com.dongyang.seoulTravel.dto.member.JoinRequest;
import com.dongyang.seoulTravel.dto.member.LoginRequest;
import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.repository.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void join(JoinRequest req) {
        userRepository.save(req.toEntity());
    }

    public User getLoginUserById(Long userId) {
        if(userId == null) return null;

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());

        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if(!user.getPassword().equals(req.getPassword())) {
            return null;
        }

        return user;
    }
}