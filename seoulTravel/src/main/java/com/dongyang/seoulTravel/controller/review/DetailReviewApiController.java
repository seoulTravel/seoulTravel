package com.dongyang.seoulTravel.controller.review;

import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.service.review.DetailReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DetailReviewApiController {
    @Autowired
    private DetailReviewService detailReviewService;

    @GetMapping("/review/{review_id}/detailreview")
    public ResponseEntity<List<DetailReviewDto>> detailreview(@PathVariable Integer review_id) {
        List<DetailReviewDto> dtos = detailReviewService.detailreviews(review_id);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @PostMapping("/review/{review_id}/detailreview")
    public ResponseEntity<DetailReviewDto> create(@PathVariable Integer review_id,
                                             @RequestBody DetailReviewDto dto) {
        DetailReviewDto createdDto = detailReviewService.create(review_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    @PatchMapping("/detailreview/{detail_id}")
    public ResponseEntity<DetailReviewDto> update(@PathVariable Integer detail_id,
                                             @RequestBody DetailReviewDto dto) {
        DetailReviewDto updatedDto = detailReviewService.update(detail_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("/detailreview/{detail_id}")
    public ResponseEntity<DetailReviewDto> delete(@PathVariable Integer detail_id) {
        DetailReviewDto deletedDto = detailReviewService.delete(detail_id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
