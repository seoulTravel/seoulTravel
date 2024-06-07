package com.dongyang.seoulTravel.dto.review;


import com.dongyang.seoulTravel.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Integer review_id;
    private Integer user_num;
    private Integer planner_id;
    private Integer review_rating;
    private String review_title;
    private String review_comment;
    private Instant timestamp;
    private Integer review_hits;
    private Integer review_scraps;
    private Integer review_likes;
    private Integer review_comments;


    public Review toEntity(){
        return new Review(review_id, user_num, planner_id,review_rating,review_title,review_comment,timestamp,review_hits,review_scraps,review_likes,review_comments);
    }

}
