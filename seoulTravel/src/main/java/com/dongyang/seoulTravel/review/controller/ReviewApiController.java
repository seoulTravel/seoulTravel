package com.dongyang.seoulTravel.review.controller;

import com.dongyang.seoulTravel.review.dto.ReviewDto;
import com.dongyang.seoulTravel.review.entity.Review;
import com.dongyang.seoulTravel.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController

public class ReviewApiController {

    @Autowired
    private ReviewRepository reviewRepository;

    //리스트
    @GetMapping("/reviews")
    public List<Review> index(){
        return reviewRepository.findAll();
    }
    //상세
    @GetMapping("/reviews/{review_id}")
    public Review show(@PathVariable Integer review_id) {
        return reviewRepository.findById(review_id).orElse(null);
    }
    //작성
    @PostMapping("/reviews")
    public Review create(@RequestBody ReviewDto dto) {
        Review review = dto.toEntity();
        return reviewRepository.save(review);
    }
    //수정
    @PatchMapping("/reviews/{review_id}")
    public ResponseEntity<Review> update(@PathVariable Integer review_id, @RequestBody ReviewDto dto) {
        Review review = dto.toEntity();
        Review target = reviewRepository.findById(review_id).orElse(null);
        if (target == null || review_id != review.getReview_id()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(review);
        Review updated = reviewRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    //샂게
    @DeleteMapping("/reviews/{review_id}")
    public ResponseEntity<Review> delete(@PathVariable Integer review_id) {
        Review target = reviewRepository.findById(review_id).orElse(null);
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        reviewRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
