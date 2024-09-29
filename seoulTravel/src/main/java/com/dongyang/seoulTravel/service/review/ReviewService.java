package com.dongyang.seoulTravel.service.review;

import com.dongyang.seoulTravel.dto.review.ReviewDto;
import com.dongyang.seoulTravel.entity.review.Review;
import com.dongyang.seoulTravel.repository.review.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> index() {
        return reviewRepository.findAll();
    }

    public Review show(Integer review_id) {
        return reviewRepository.findById(review_id).orElse(null);
    }

    public Review create(ReviewDto dto) {
        Review review = dto.toEntity();
        if (review.getReview_id() != null) {
            return null;
        }
        return reviewRepository.save(review);
    }

    public Review update(Integer review_id, ReviewDto dto) {
        Review review = dto.toEntity();
        Review target = reviewRepository.findById(review_id).orElse(null);

        if (target == null || review_id != review.getReview_id()) {
            return null;
        }

        target.patch(review);
        return reviewRepository.save(target);
    }

    public Review delete(Integer review_id) {
        Review target = reviewRepository.findById(review_id).orElse(null);

        if (target == null) {
            return null;
        }

        reviewRepository.delete(target);
        return target;
    }

    // 사용자 번호로 리뷰 조회
    public List<Review> getReviewsByUserNum(Integer userNum) {
        return reviewRepository.findByUserNum(userNum);
    }
}
