package com.dongyang.seoulTravel.entity.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer review_id;
    @Column
    private Integer user_num;
    @Column
    private Integer planner_id;
    @Column
    private Integer review_rating;
    @Column
    private String review_title;
    @Column
    private String review_comment;
    @Column(updatable = false)
    private Instant timestamp;
    @PrePersist
    protected void onCreate() {
        this.timestamp = Instant.now();
    }
    @Column
    private Instant review_hits;
    @Column
    private Instant review_scraps;
    @Column
    private Instant review_likes;
    @Column
    private Instant review_Comments;

    //태그데이터, 사진 추가


    public void patch(Review review) {
        if(review.planner_id != null)
            this.planner_id = review.planner_id;
        if(review.review_rating != null)
            this.review_rating = review.review_rating;
        if(review.review_title != null)
            this.review_title = review.review_title;
        if(review.review_comment != null)
            this.review_comment = review.review_comment;
        this.timestamp = Instant.now();
    }


}
