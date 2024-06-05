package com.dongyang.seoulTravel.controller.review;

import com.dongyang.seoulTravel.dto.review.CommentDto;
import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.service.review.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/review/{review_id}/comment")
    public ResponseEntity<List<CommentDto>> comment(@PathVariable Integer review_id) {
        List<CommentDto> dtos = commentService.comments(review_id);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @PostMapping("/review/{review_id}/comment")
    public ResponseEntity<CommentDto> create(@PathVariable Integer review_id,
                                                  @RequestBody CommentDto dto) {
        CommentDto createdDto = commentService.create(review_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    @PatchMapping("/comment/{comment_id}")
    public ResponseEntity<CommentDto> update(@PathVariable Integer comment_id,
                                                  @RequestBody CommentDto dto) {
        CommentDto updatedDto = commentService.update(comment_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Integer comment_id) {
        CommentDto commentDto = commentService.delete(comment_id);

        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

}
