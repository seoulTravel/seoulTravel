package com.dongyang.seoulTravel.entity.review;

import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detailreview")
public class DetailReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detail_id;
    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;
    @Column
    private Integer detail_day;
    @Column
    private Integer detail_order;
    @Column
    private String detail_title;//장소이름
    @Column
    private String detail_comment;
    @Column
    private Integer detail_rating;

    // 사진 목록 추가


    public static DetailReview createDetailReview(DetailReviewDto dto, Review review) {
        if (dto.getReview_id() != review.getReview_id())
            throw new IllegalArgumentException("상위문서 없음");

        return new DetailReview(
                dto.getDetail_id(),
                review,
                dto.getDetail_day(),
                dto.getDetail_order(),
                dto.getDetail_title(),
                dto.getDetail_comment(),
                dto.getDetail_rating()
        );
    }
    public void patch(DetailReviewDto dto) {

        if (this.detail_id != dto.getDetail_id())
            throw new IllegalArgumentException("잘못된 디테일 주소");

        if (dto.getDetail_day() != null)
            this.detail_day = dto.getDetail_day();

        if (dto.getDetail_order() != null)
            this.detail_order = dto.getDetail_order();
        if (dto.getDetail_title() != null)
            this.detail_title = dto.getDetail_title();
        if (dto.getDetail_comment() != null)
            this.detail_comment = dto.getDetail_comment();
        if (dto.getDetail_rating() != null)
            this.detail_rating = dto.getDetail_rating();
    }

}
