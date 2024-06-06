package com.dongyang.seoulTravel.entity.review;

import com.dongyang.seoulTravel.dto.review.CommentDto;
import com.dongyang.seoulTravel.dto.review.ReplyDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reply_id;
    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;
    @Column
    private Integer user_num;
    @Column
    private String comment_text;
    @Column(updatable = false)
    private Instant timestamp;
    @PrePersist
    protected void onCreate() {
        this.timestamp = Instant.now();
    }
    //사진파일추가


    public static Reply createReply(ReplyDto dto, Comment comment) {
        if (dto.getComment_id() != comment.getComment_id())
            throw new IllegalArgumentException("상위문서 없음");

        return new Reply(
                dto.getReply_id(),
                comment,
                dto.getUser_num(),
                dto.getComment_text(),
                dto.getTimestamp()
        );
    }
    public void patch(ReplyDto dto) {

        if (this.reply_id != dto.getReply_id())
            throw new IllegalArgumentException("잘못된 댓글 주소");
        if (dto.getUser_num() != null)
            this.user_num = dto.getUser_num();
        if (dto.getComment_text() != null)
            this.comment_text = dto.getComment_text();
        if (dto.getTimestamp() != null)
            this.timestamp = dto.getTimestamp();

    }
}
