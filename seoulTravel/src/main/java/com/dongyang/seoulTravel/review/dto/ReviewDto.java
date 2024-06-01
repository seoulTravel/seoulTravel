package com.dongyang.seoulTravel.review.dto;


import com.dongyang.seoulTravel.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@ToString
public class ReviewDto {
    private Integer review_id;
    private Integer user_num;
    private Integer planner_id;
    private Integer review_rating;
    private String review_title;
    private String review_comment;
    private Instant timestamp;


    public Review toEntity(){
        return new Review(review_id, user_num, planner_id,review_rating,review_title,review_comment,timestamp);
    }

}
