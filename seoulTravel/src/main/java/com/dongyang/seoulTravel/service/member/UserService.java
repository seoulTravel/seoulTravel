package com.dongyang.seoulTravel.service.member;

import com.dongyang.seoulTravel.dto.member.UserDTO;
import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.repository.member.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;  // 이메일 전송을 위한 JavaMailSender 주입

    public User login(UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUser_Id());
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        if (!user.getUser_password().equals(dto.getUser_password())) {
            return null;
        }
        return user;
    }

    @Transactional
    public UserDTO create(String id, UserDTO dto) {
        User user = userRepository.findByUserId(id).orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
        User created = userRepository.save(user);
        return UserDTO.createUserDTO(created);
    }

    @Transactional
    public UserDTO update(String id, UserDTO dto) {
        User target = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
        target.patch(dto);
        User updated = userRepository.save(target);
        return UserDTO.createUserDTO(updated);
    }

    @Transactional
    public UserDTO delete(String id) {
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

    // 이메일을 통한 비밀번호 재설정 (토큰 방식)
    public void sendPasswordResetEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일로 등록된 회원이 없습니다.");
        }
        String resetToken = UUID.randomUUID().toString();
        String resetLink = "http://your-domain/reset-password?token=" + resetToken;
        sendEmail(user.get().getUser_email(), "비밀번호 재설정 안내", "비밀번호 재설정 링크: " + resetLink);
    }

    // 비밀번호 재설정 (랜덤 비밀번호 생성)
    @Transactional
    public void resetPasswordByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 등록된 회원이 없습니다."));

        // 랜덤 비밀번호 생성
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        user.setUser_password(newPassword);
        userRepository.save(user);

        // 이메일 전송
        sendEmail(user.getUser_email(), "비밀번호 재설정 안내", "새 비밀번호는: " + newPassword);
    }

    // 이메일 전송 메서드
    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);  // HTML을 지원하는 이메일

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("이메일 전송 실패");
        }
    }
}
