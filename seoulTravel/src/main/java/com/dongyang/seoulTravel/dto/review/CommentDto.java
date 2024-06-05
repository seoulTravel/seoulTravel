package com.dongyang.seoulTravel.dto.review;


import com.dongyang.seoulTravel.entity.review.Comment;
import com.dongyang.seoulTravel.entity.review.DetailReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

public class CommentDto {
    private Integer comment_id;
    private Integer review_id;
    private Integer user_num;
    private String comment_text;
    private Instant timestamp;

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getComment_id(),
                comment.getReview().getReview_id(),
                comment.getUser_num(),
                comment.getComment_text(),
                comment.getTimestamp()

        );
    }
}
