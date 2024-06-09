package com.dongyang.seoulTravel.repository.review;

import com.dongyang.seoulTravel.entity.review.DetailPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailPhotoRepository extends JpaRepository<DetailPhoto, Integer> {
    @Query(value="SELECT * FROM detailPhoto WHERE detail_id = :detailid",
            nativeQuery = true)
    List<DetailPhoto> findByDetailId(Integer detailid);
}
