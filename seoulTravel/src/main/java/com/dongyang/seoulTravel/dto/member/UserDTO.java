package com.dongyang.seoulTravel.dto.member;

import com.dongyang.seoulTravel.entity.member.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDTO {
    private String user_Id;
    private String user_name;
    private String user_password;
    private String user_email;
    private String user_sexual;
    private String file_id;
    private String favorite_theme;

    public static UserDTO createUserDTO(User user) {
        return new UserDTO(
                user.getUser_Id(),
                user.getUser_name(),
                user.getUser_password(),
                user.getUser_email(),
                user.getUser_sexual(),
                user.getFile_id(),
                user.getFavorite_theme()
        );
    }
}
