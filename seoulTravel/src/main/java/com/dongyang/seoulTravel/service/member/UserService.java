package com.dongyang.seoulTravel.service.member;

import com.dongyang.seoulTravel.dto.member.UserDTO;
import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.repository.member.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public UserDTO create(String email) {
        User userEmail = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));


        User created = userRepository.save(userEmail);
        return UserDTO.createUserDTO(created);
    }
    @Transactional
    public UserDTO update(String email, UserDTO dto){
        User target = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
        target.patch(dto);

        User updated = userRepository.save(target);
        return UserDTO.createUserDTO(updated);
    }
    @Transactional
    public UserDTO delete(String email){
        User target = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));

        userRepository.delete(target);
        return UserDTO.createUserDTO(target);
    }
}
