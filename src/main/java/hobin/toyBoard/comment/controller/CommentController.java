package hobin.toyBoard.comment.controller;

import hobin.toyBoard.comment.dto.CommentDto;
import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.comment.mapper.CommentMapper;
import hobin.toyBoard.comment.service.CommentService;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Validated
@Slf4j
public class CommentController {

    private final CommentMapper mapper;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity postComment(
            @RequestParam("memberId") @Positive Long memberId,
            @RequestParam("boardId") @Positive Long boardId,
            @Valid @RequestBody CommentDto.Post commnetPostDto) {
        Comment saveComment = commentService.createComment(mapper.CommentPostDtoToComment(commnetPostDto), memberId, boardId);
        log.info(saveComment.getCommentId().toString());
        return new ResponseEntity<>(mapper.CommentToCommentResponseDto(saveComment), HttpStatus.CREATED);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(@PathVariable("comment-id") @Positive Long commentId,
                                       @Valid @RequestBody CommentDto.Patch commentPatchDto) {
        Comment findComment = commentService.updateComment(mapper.CommentPatchDtoToComment(commentPatchDto));
        return new ResponseEntity<>(mapper.CommentToCommentResponseDto(findComment), HttpStatus.OK);
    }

    @GetMapping("/{comment-id}")
    public ResponseEntity getComment(@PathVariable("comment-id") @Positive Long commentId) {
        Comment findComment = commentService.findComment(commentId);
        return new ResponseEntity<>(mapper.CommentToCommentResponseDto(findComment), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getComments(@RequestParam("page") @Positive int page,
                                      @RequestParam("size") @Positive int size) {
        List<Comment> findAll = commentService.findAll(page, size).getContent();
        return new ResponseEntity<>(mapper.CommentsToCommentResponseDtos(findAll), HttpStatus.OK);
    }

    @GetMapping("/bymember/{member-id}")
    public ResponseEntity getCommentsByMember(@PathVariable("member-id") @Positive Long memberId,
                                              @RequestParam("page") @Positive int page,
                                              @RequestParam("size") @Positive int size) {
        List<Comment> findAllByMember = commentService.findAllByMember(memberId, page, size);
        return new ResponseEntity<>(mapper.CommentsToCommentResponseDtos(findAllByMember), HttpStatus.OK);
    }

    @GetMapping("/byboard/{board-id}")
    public ResponseEntity getCommentsByBoard(@PathVariable("board-id") @Positive Long boardId,
                                             @RequestParam("page") @Positive int page,
                                             @RequestParam("size") @Positive int size) {
        List<Comment> findAllByBoard = commentService.findAllByBoard(boardId, page, size).getContent();
        return new ResponseEntity<>(mapper.CommentsToCommentResponseDtos(findAllByBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
