package com.dongyang.seoulTravel.entity.review;

import com.dongyang.seoulTravel.dto.review.CommentDto;
import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_id;
    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;
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
    //별점, 사진파일 추가


    public static Comment createComment(CommentDto dto, Review review) {
        if (dto.getReview_id() != review.getReview_id())
            throw new IllegalArgumentException("상위문서 없음");

        return new Comment(
                dto.getComment_id(),
                review,
                dto.getUser_num(),
                dto.getComment_text(),
                dto.getTimestamp()
        );
    }
    public void patch(CommentDto dto) {
        if (this.comment_id != dto.getComment_id())
            throw new IllegalArgumentException("잘못된 댓글 주소");
        if (dto.getUser_num() != null)
            this.user_num = dto.getUser_num();
        if (dto.getComment_text() != null)
            this.comment_text = dto.getComment_text();
        if (dto.getTimestamp() != null)
            this.timestamp = dto.getTimestamp();

    }



}
