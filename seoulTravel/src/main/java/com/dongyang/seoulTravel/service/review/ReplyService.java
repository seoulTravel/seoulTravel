package com.dongyang.seoulTravel.service.review;

import com.dongyang.seoulTravel.dto.review.DetailReviewDto;
import com.dongyang.seoulTravel.dto.review.ReplyDto;
import com.dongyang.seoulTravel.entity.review.Comment;
import com.dongyang.seoulTravel.entity.review.DetailReview;
import com.dongyang.seoulTravel.entity.review.Reply;
import com.dongyang.seoulTravel.entity.review.Review;
import com.dongyang.seoulTravel.repository.review.CommentRepository;
import com.dongyang.seoulTravel.repository.review.ReplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<ReplyDto> replys(Integer comment_id) {
        List<Reply> replys = replyRepository.findByCommentId(comment_id);

        List<ReplyDto> dtos = new ArrayList<ReplyDto>();
        for (int i = 0; i < replys.size(); i++) {
            Reply r = replys.get(i);
            ReplyDto dto = ReplyDto.createReplyDto(r);
            dtos.add(dto);
        }

        return replyRepository.findByCommentId(comment_id)
                .stream()
                .map(reply -> ReplyDto.createReplyDto(reply))
                .collect(Collectors.toList());
    }
    @Transactional
    public ReplyDto create(Integer comment_id, ReplyDto dto) {
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("상위문성 없음"));

        Reply reply = Reply.createReply(dto, comment);

        Reply created = replyRepository.save(reply);

        return ReplyDto.createReplyDto(created);
    }

    public ReplyDto update(Integer reply_id, ReplyDto dto) {
        Reply target = replyRepository.findById(reply_id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 문서 없음"));

        target.patch(dto);

        Reply updated = replyRepository.save(target);

        return ReplyDto.createReplyDto(updated);
    }
    @Transactional
    public ReplyDto delete(Integer reply_id) {
        Reply target = replyRepository.findById(reply_id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 문서 없음"));

        replyRepository.delete(target);

        return ReplyDto.createReplyDto(target);
    }
}
