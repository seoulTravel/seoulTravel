package com.dongyang.seoulTravel.controller.review;

import com.dongyang.seoulTravel.dto.review.DetailPhotoDto;
import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.service.review.DetailPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DetailPhotoApiController {
    @Autowired
    private DetailPhotoService detailPhotoService;

    @GetMapping("/detailreview/{detail_id}/detailphoto")
    public ResponseEntity<List<DetailPhotoDto>> detailphoto(@PathVariable Integer detail_id) {
        List<DetailPhotoDto> dtos = detailPhotoService.detailphotos(detail_id);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @PostMapping("/detailreview/{detail_id}/detailphoto")
    public ResponseEntity<DetailPhotoDto> create(@PathVariable Integer detail_id,
                                                  @RequestBody DetailPhotoDto dto) {
        DetailPhotoDto createdDto = detailPhotoService.create(detail_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    @PatchMapping("/detailphoto/{photo_id}")
    public ResponseEntity<DetailPhotoDto> update(@PathVariable Integer photo_id,
                                                  @RequestBody DetailPhotoDto dto) {
        DetailPhotoDto updatedDto = detailPhotoService.update(photo_id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("/detailphoto/{photo_id}")
    public ResponseEntity<DetailPhotoDto> delete(@PathVariable Integer photo_id) {
        DetailPhotoDto deletedDto = detailPhotoService.delete(photo_id);

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
