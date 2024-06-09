package com.dongyang.seoulTravel.dto.review;


import com.dongyang.seoulTravel.entity.review.DetailPhoto;
import com.dongyang.seoulTravel.entity.review.DetailReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

public class DetailPhotoDto {
    private Integer photo_id;
    private Integer detail_id;
    private String url;
    private String type;

    public static DetailPhotoDto createDetailPhotoDto(DetailPhoto detailPhoto) {
        return new DetailPhotoDto(
                detailPhoto.getPhoto_id(),
                detailPhoto.getDetailReview().getDetail_id(),
                detailPhoto.getUrl(),
                detailPhoto.getType()
        );
    }

}
