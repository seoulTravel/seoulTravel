package com.dongyang.seoulTravel.service.review;

import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.entity.review.DetailReview;
import com.dongyang.seoulTravel.entity.review.Review;
import com.dongyang.seoulTravel.repository.review.DetailReviewRepository;
import com.dongyang.seoulTravel.repository.review.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailReviewService {
    @Autowired
    private DetailReviewRepository detailReviewRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<DetailReviewDto> detailreviews(Integer review_id) {
        List<DetailReview> detailreviews = detailReviewRepository.findByReviewId(review_id);

        List<DetailReviewDto> dtos = new ArrayList<DetailReviewDto>();
        for (int i = 0; i < detailreviews.size(); i++) {
            DetailReview d = detailreviews.get(i);
            DetailReviewDto dto = DetailReviewDto.createDetailReviewDto(d);
            dtos.add(dto);
        }

        return detailReviewRepository.findByReviewId(review_id)
                .stream()
                .map(detailreview -> DetailReviewDto.createDetailReviewDto(detailreview))
                .collect(Collectors.toList());
    }
    @Transactional
    public DetailReviewDto create(Integer review_id, DetailReviewDto dto) {
        Review review = reviewRepository.findById(review_id)
                .orElseThrow(() -> new IllegalArgumentException("상위문성 없음"));

        DetailReview detailReview = DetailReview.createDetailReview(dto, review);

        DetailReview created = detailReviewRepository.save(detailReview);

        return DetailReviewDto.createDetailReviewDto(created);
    }

    public DetailReviewDto update(Integer detail_id, DetailReviewDto dto) {
        DetailReview target = detailReviewRepository.findById(detail_id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 문서 없음"));

        target.patch(dto);

        DetailReview updated = detailReviewRepository.save(target);

        return DetailReviewDto.createDetailReviewDto(updated);
    }
    @Transactional
    public DetailReviewDto delete(Integer detail_id) {
        DetailReview target = detailReviewRepository.findById(detail_id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 문서 없음"));

        detailReviewRepository.delete(target);

        return DetailReviewDto.createDetailReviewDto(target);
    }
}

