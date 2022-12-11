package hobin.toyBoard.comment.service;

import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.service.BoardService;
import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.comment.repository.CommentRepository;
import hobin.toyBoard.exception.BussinessLogicException;
import hobin.toyBoard.exception.ExceptionCode;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public Comment createComment(Comment comment, Long memberId, Long boardId) {
        Member findMember = memberService.findMember(memberId);
        Board findBoard = boardService.findBoard(boardId);

        comment.createComment(findMember, findBoard);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(findVerifiedComment(comment.getCommentId()));
    }

    public Comment findComment(Long commentId) {
        return findVerifiedComment(commentId);
    }

    public Page<Comment> findAll(int page, int size) {

        return commentRepository.findAll(PageRequest.of(page - 1, size,
                Sort.by("commentId").descending()));
    }

    public void deleteComment(Long commentId) {
        commentRepository.delete(findVerifiedComment(commentId));
    }

    public Comment findVerifiedComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        return optionalComment
                .orElseThrow(() -> new BussinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }
}
