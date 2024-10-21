package com.dongyang.seoulTravel.repository.review;

import com.dongyang.seoulTravel.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // 사용자 번호로 리뷰 조회
    List<Review> findByUserNum(Integer userNum);
}
