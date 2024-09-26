package com.dongyang.seoulTravel.service.member;

import com.dongyang.seoulTravel.dto.member.UserDTO;
import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.repository.member.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User login(UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUser_Id());
        if(optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        if(!user.getUser_password().equals(dto.getUser_password())) {
            return null;
        }
        return user;
    }


    @Transactional
    public UserDTO create(String id, UserDTO dto) {

        User user = userRepository.findByUserId(id).orElseThrow(()-> new IllegalArgumentException("아이디 없음"));

        User created = userRepository.save(user);
        return UserDTO.createUserDTO(created);
    }

    @Transactional
    public UserDTO update(String id, UserDTO dto){
        User target = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
        target.patch(dto);

        User updated = userRepository.save(target);
        return UserDTO.createUserDTO(updated);
    }
    @Transactional
    public UserDTO delete(String id){
        User target = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));

        userRepository.delete(target);
        return UserDTO.createUserDTO(target);
    }

    // 아이디 찾기
    public String findUserIdByEmailOrName(String email, String name) {
        Optional<User> user = userRepository.findByEmailOrName(email, name);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일 또는 이름으로 등록된 회원이 없습니다.");
        }
        return user.get().getUser_Id();
    }

    // 이메일을 통한 비밀번호 재설정
    public void sendPasswordResetEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일로 등록된 회원이 없습니다.");
        }
        String resetToken = UUID.randomUUID().toString();

        String resetLink = "http://(도메인 변경)/reset-password?token=" + resetToken;
        sendEmail(user.get().getUser_Id(), resetLink);
    }

    private void sendEmail(String to, String resetLink) {
        // 이메일 전송 로직을 구현
        System.out.println("비밀번호 재설정 링크: " + resetLink);
    }
}
