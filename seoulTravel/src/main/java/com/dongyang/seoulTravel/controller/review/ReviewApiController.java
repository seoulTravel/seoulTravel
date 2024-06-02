package com.dongyang.seoulTravel.controller.review;

import com.dongyang.seoulTravel.dto.review.ReviewDto;
import com.dongyang.seoulTravel.entity.review.Review;
import com.dongyang.seoulTravel.repository.review.ReviewRepository;
import com.dongyang.seoulTravel.service.review.ReviewService;
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
    private ReviewService reviewService;

    //리스트
    @GetMapping("/reviews")
    public List<Review> index(){
        return reviewService.index();
    }
    //상세
    @GetMapping("/reviews/{review_id}")
    public Review show(@PathVariable Integer review_id) {
        return reviewService.show(review_id);    }
    //작성
    @PostMapping("/reviews")
    public ResponseEntity<Review> create(@RequestBody ReviewDto dto) {
        Review created = reviewService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //수정
    @PatchMapping("/reviews/{review_id}")
    public ResponseEntity<Review> update(@PathVariable Integer review_id, @RequestBody ReviewDto dto) {
        Review updated = reviewService.update(review_id, dto);
        return (updated !=null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //삭제
    @DeleteMapping("/reviews/{review_id}")
    public ResponseEntity<Review> delete(@PathVariable Integer review_id) {
        Review deleted = reviewService.delete(review_id);
        return (deleted !=null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
