package com.dongyang.seoulTravel.entity.member;

import com.dongyang.seoulTravel.dto.member.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
    private String user_email;
    private String user_sexual;
    private String file_id;
    private String favorite_theme;
    //친구목록
    @OneToMany(mappedBy = "user")
    private List<Friendship> friendshipList = new ArrayList<>();

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
        if(dto.getUser_email() != null){
            this.user_email = dto.getUser_email();
        }
        if(dto.getUser_password() != null) {
            this.user_password = dto.getUser_password();
        }
    }
}
