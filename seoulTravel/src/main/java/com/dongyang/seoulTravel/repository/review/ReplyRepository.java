package com.dongyang.seoulTravel.repository.review;

import com.dongyang.seoulTravel.entity.review.Comment;
import com.dongyang.seoulTravel.entity.review.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Query(value="SELECT * FROM reply WHERE comment_id = :commentid",
            nativeQuery = true)
    List<Reply> findByCommentId(Integer commentid);
}
