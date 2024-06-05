package com.dongyang.seoulTravel.repository.review;

import com.dongyang.seoulTravel.entity.review.Comment;
import com.dongyang.seoulTravel.entity.review.DetailReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value="SELECT * FROM comment WHERE review_id = :reviewid",
            nativeQuery = true)
    List<Comment> findByReviewId(Integer reviewid);
}
