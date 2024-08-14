package com.dongyang.seoulTravel.controller.member;

import com.dongyang.seoulTravel.dto.member.UserDTO;
import com.dongyang.seoulTravel.entity.member.User;
import com.dongyang.seoulTravel.service.member.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApiController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO dto, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest) {
        User user = userService.login(dto);

        if(user == null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "sessionLogin/login";
        }
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", user.getUser_Id());
        session.setMaxInactiveInterval(1800);
        return "redirect:/session-login";
    }

    @PostMapping("/{user_email}")
    public ResponseEntity<UserDTO> create(@PathVariable String user_email,
                                          @RequestBody UserDTO dto){
        UserDTO userDto = userService.create(user_email);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
    @PatchMapping("/{user_email}")
    public ResponseEntity<UserDTO> update(@PathVariable String user_email,
                                          @RequestBody UserDTO dto){
        UserDTO userDTO = userService.update(user_email, dto);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
    @DeleteMapping("/{user_email}")
    public ResponseEntity<UserDTO> delete(@PathVariable String user_email){
        UserDTO userDTO = userService.delete(user_email);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
