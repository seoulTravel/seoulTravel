package com.dongyang.seoulTravel.service.review;

import com.dongyang.seoulTravel.dto.review.CommentDto;
import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.entity.review.Comment;
import com.dongyang.seoulTravel.entity.review.DetailReview;
import com.dongyang.seoulTravel.entity.review.Review;
import com.dongyang.seoulTravel.repository.review.ReviewRepository;
import com.dongyang.seoulTravel.repository.review.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDto> comments(Integer review_id) {
        List<Comment> comments = commentRepository.findByReviewId(review_id);

        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }

        return commentRepository.findByReviewId(review_id)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }
    @Transactional
    public CommentDto create(Integer review_id, CommentDto dto) {
        Review review = reviewRepository.findById(review_id)
                .orElseThrow(() -> new IllegalArgumentException("상위문성 없음"));

        Comment comment = Comment.createComment(dto, review);

        Comment created = commentRepository.save(comment);

        return CommentDto.createCommentDto(created);
    }
    @Transactional
    public CommentDto update(Integer comment_id, CommentDto dto) {
        Comment target = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 문서 없음"));

        target.patch(dto);

        Comment updated = commentRepository.save(target);

        return CommentDto.createCommentDto(updated);
    }
    @Transactional
    public CommentDto delete(Integer comment_id) {
        Comment target = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 문서 없음"));

        commentRepository.delete(target);

        return CommentDto.createCommentDto(target);
    }
}
