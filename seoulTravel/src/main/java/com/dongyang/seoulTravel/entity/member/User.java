package com.dongyang.seoulTravel.entity.member;

import com.dongyang.seoulTravel.dto.member.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String user_Id;          // 아이디
    private String user_name;        // 이름
    private String user_password;    // 비밀번호
    private String user_email;       // 이메일
    private String user_sexual;      // 성별
    private String file_id;          // 파일 ID
    private String favorite_theme;    // 선호 테마

    public void patch(UserDTO dto) {
        if (this.user_Id.equals(dto.getUser_Id())) {
            throw new IllegalArgumentException("잘못된 아이디");
        }
        if (dto.getUser_name() != null) {
            this.user_name = dto.getUser_name();
        }
        if (dto.getUser_sexual() != null) {
            this.user_sexual = dto.getUser_sexual();
        }
        if (dto.getUser_email() != null) {
            this.user_email = dto.getUser_email();
        }
        if (dto.getUser_password() != null) {
            this.user_password = dto.getUser_password();
        }
    }
}
