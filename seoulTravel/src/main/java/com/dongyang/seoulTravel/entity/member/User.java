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
    private String user_Id;
    private String user_name;
    private String user_password;
    private String user_sexual;
    private String file_id;

    public void patch(UserDTO dto){
        if(this.user_Id.equals(dto.getUser_Id())){
            throw new IllegalArgumentException("잘못된 아이디");
        }
        if(dto.getUser_name() != null){
            this.user_name = dto.getUser_name();
        }
        if(dto.getUser_sexual() != null){
            this.user_sexual = dto.getUser_sexual();
        }
        if(dto.getUser_password() != null){
            this.user_password = dto.getUser_password();
        }
    }
}
