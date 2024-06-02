package com.dongyang.seoulTravel.dto.review;

import com.dongyang.seoulTravel.entity.review.DetailReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

public class DetailReviewDto {
    private Integer detail_id;
    private Integer review_id;
    private Integer detail_day;
    private Integer detail_order;
    private String detail_title;
    private String detail_comment;
    private Integer detail_rating;


    public static DetailReviewDto createDetailReviewDto(DetailReview detailReview) {
        return new DetailReviewDto(
                detailReview.getDetail_id(),
                detailReview.getReview().getReview_id(),
                detailReview.getDetail_day(),
                detailReview.getDetail_order(),
                detailReview.getDetail_title(),
                detailReview.getDetail_comment(),
                detailReview.getDetail_rating()
        );
    }

}
