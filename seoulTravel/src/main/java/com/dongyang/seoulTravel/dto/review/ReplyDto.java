package com.dongyang.seoulTravel.dto.review;

import com.dongyang.seoulTravel.entity.review.Comment;
import com.dongyang.seoulTravel.entity.review.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

public class ReplyDto {
    private Integer reply_id;
    private Integer comment_id;
    private Integer user_num;
    private String comment_text;
    private Instant timestamp;

    public static ReplyDto createReplyDto(Reply reply) {
        return new ReplyDto(
                reply.getReply_id(),
                reply.getComment().getComment_id(),
                reply.getUser_num(),
                reply.getComment_text(),
                reply.getTimestamp()

        );
    }

}
