package com.dongyang.seoulTravel.entity.review;


import com.dongyang.seoulTravel.dto.review.DetailPhotoDto;
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
@Table(name = "detailphoto")
public class DetailPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photo_id;
    @ManyToOne
    @JoinColumn(name="detail_id")
    private DetailReview detailReview;
    @Column
    private String url;
    @Column
    private String type;

    public static DetailPhoto createDetailPhoto(DetailPhotoDto dto, DetailReview detailreview) {
        if (dto.getDetail_id() != detailreview.getDetail_id())
            throw new IllegalArgumentException("상위문서 없음");

        return new DetailPhoto(
                dto.getPhoto_id(),
                detailreview,
                dto.getUrl(),
                dto.getType()
        );
    }
    public void patch(DetailPhotoDto dto) {

        if (this.photo_id != dto.getPhoto_id())
            throw new IllegalArgumentException("잘못된 디테일 주소");

        if (dto.getUrl() != null)
            this.url = dto.getUrl();

        if (dto.getType() != null)
            this.type = dto.getType();

    }
}
