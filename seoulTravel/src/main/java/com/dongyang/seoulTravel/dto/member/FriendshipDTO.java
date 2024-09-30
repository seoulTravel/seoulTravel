package com.dongyang.seoulTravel.dto.member;

import com.dongyang.seoulTravel.entity.member.Friendship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class FriendshipDTO {
    private Integer id;
    private String user_email;
    private String friend_email;
    private String status;
    private boolean isFrom;
    private Integer counterpartId;

    // Friendship 엔티티를 DTO로 변환하는 메서드
    public static FriendshipDTO fromEntity(Friendship friendship) {
        return new FriendshipDTO(
                friendship.getId(),
                friendship.getUser().getUser_email(), // User의 이메일 가져오기
                friendship.getFriend_email(),
                friendship.getStatus().toString(), // enum을 문자열로 변환
                friendship.isFrom(),
                friendship.getCounterpartId()
        );
    }
}
