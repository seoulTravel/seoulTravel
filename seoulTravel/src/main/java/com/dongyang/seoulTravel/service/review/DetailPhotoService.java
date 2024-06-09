package com.dongyang.seoulTravel.service.review;

import com.dongyang.seoulTravel.dto.review.DetailPhotoDto;
import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.entity.review.DetailPhoto;
import com.dongyang.seoulTravel.entity.review.DetailReview;
import com.dongyang.seoulTravel.repository.review.DetailPhotoRepository;
import com.dongyang.seoulTravel.repository.review.DetailReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailPhotoService {
    @Autowired
    private DetailPhotoRepository detailPhotoRepository;
    @Autowired
    private DetailReviewRepository detailReviewRepository;

    public List<DetailPhotoDto> detailphotos(Integer detail_id) {
        List<DetailPhoto> detailphotos = detailPhotoRepository.findByDetailId(detail_id);

        List<DetailPhotoDto> dtos = new ArrayList<DetailPhotoDto>();
        for (int i = 0; i < detailphotos.size(); i++) {
            DetailPhoto d = detailphotos.get(i);
            DetailPhotoDto dto = DetailPhotoDto.createDetailPhotoDto(d);
            dtos.add(dto);
        }
        return detailPhotoRepository.findByDetailId(detail_id)
                .stream()
                .map(detailphoto -> DetailPhotoDto.createDetailPhotoDto(detailphoto))
                .collect(Collectors.toList());
    }

    @Transactional
    public DetailPhotoDto create(Integer detailReview_id, DetailPhotoDto dto) {
        DetailReview detailReview = detailReviewRepository.findById(detailReview_id)
                .orElseThrow(() -> new IllegalArgumentException("상위 리뷰 없음"));

        DetailPhoto detailPhoto = DetailPhoto.createDetailPhoto(dto, detailReview);

        DetailPhoto created = detailPhotoRepository.save(detailPhoto);

        return DetailPhotoDto.createDetailPhotoDto(created);
    }

    @Transactional
    public DetailPhotoDto update(Integer photo_id, DetailPhotoDto dto) {
        DetailPhoto target = detailPhotoRepository.findById(photo_id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 사진 없음"));

        target.patch(dto);

        DetailPhoto updated = detailPhotoRepository.save(target);

        return DetailPhotoDto.createDetailPhotoDto(updated);
    }

    @Transactional
    public DetailPhotoDto delete(Integer photo_id) {
        DetailPhoto target = detailPhotoRepository.findById(photo_id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 사진 없음"));

        detailPhotoRepository.delete(target);

        return DetailPhotoDto.createDetailPhotoDto(target);
    }
}
