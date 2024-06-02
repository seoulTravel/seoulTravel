package com.dongyang.seoulTravel.repository.review;

import com.dongyang.seoulTravel.entity.review.DetailReview;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailReviewRepository extends JpaRepository<DetailReview, Integer> {
    @Query(value="SELECT * FROM detailReview WHERE review_id = :reviewid",
    nativeQuery = true)
    List<DetailReview> findByReviewId(Integer reviewid);
    
}
