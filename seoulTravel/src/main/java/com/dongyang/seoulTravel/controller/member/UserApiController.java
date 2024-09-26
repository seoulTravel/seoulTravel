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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApiController {
    @Autowired
    private UserService userService;

    // 로그인
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

    // 회원가입
    @PostMapping("/{user_id}")
    public ResponseEntity<UserDTO> create(@PathVariable String user_id,
                                          @RequestBody UserDTO dto){
        UserDTO userDto = userService.create(user_id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
    
    // 멤버 수정
    @PatchMapping("/{user_id}")
    public ResponseEntity<UserDTO> update(@PathVariable String user_id,
                                          @RequestBody UserDTO dto){
        UserDTO userDTO = userService.update(user_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
    
    // 회원 삭제
    @DeleteMapping("/{user_id}")
    public ResponseEntity<UserDTO> delete(@PathVariable String user_id){
        UserDTO userDTO = userService.delete(user_id);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }


    // 아이디 찾기 (이름과 이메일로 확인)
    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestParam String email, @RequestParam String name) {
        String userId = userService.findUserIdByEmailOrName(email, name);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
}
