package com.dongyang.seoulTravel.controller.review;

import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.dto.review.ReplyDto;
import com.dongyang.seoulTravel.service.review.DetailReviewService;
import com.dongyang.seoulTravel.service.review.ReplyService;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReplyApiController {
    @Autowired
    private ReplyService replyService;


    @GetMapping("/comment/{comment_id}/reply")
    public ResponseEntity<List<ReplyDto>> reply(@PathVariable Integer comment_id) {
        List<ReplyDto> dtos = replyService.replys(comment_id);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @PostMapping("/comment/{comment_id}/reply")
    public ResponseEntity<ReplyDto> create(@PathVariable Integer comment_id,
                                                  @RequestBody ReplyDto dto) {
        ReplyDto createdDto = replyService.create(comment_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    @PatchMapping("/reply/{reply_id}")
    public ResponseEntity<ReplyDto> update(@PathVariable Integer reply_id,
                                                  @RequestBody ReplyDto dto) {
        ReplyDto updatedDto = replyService.update(reply_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("/reply/{reply_id}")
    public ResponseEntity<ReplyDto> delete(@PathVariable Integer reply_id) {
        ReplyDto deletedDto = replyService.delete(reply_id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
