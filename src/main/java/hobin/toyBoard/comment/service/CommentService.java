package hobin.toyBoard.comment.service;

import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Comment comment) {
        Comment findComment = findVerifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getContent())
                .ifPresent(content -> findComment.changeContent(content));
        return null;
    }

    public Comment findComment(Long commentId) {
        return findVerifiedComment(commentId);
    }

    public Page<Comment> findAll(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page - 1, size,
                Sort.by("commentId").descending()));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.delete(findVerifiedComment(commentId));
    }

    public Comment findVerifiedComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment = optionalComment
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        return findComment;
    }
}
