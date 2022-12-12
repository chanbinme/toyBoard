package hobin.toyBoard.like.service;

import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.service.BoardService;
import hobin.toyBoard.exception.BussinessLogicException;
import hobin.toyBoard.exception.ExceptionCode;
import hobin.toyBoard.like.entity.Like;
import hobin.toyBoard.like.repository.LikeRepository;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public Like createLike(Long membeId, Long boardId) {
        Member findMember = memberService.findMember(membeId);
        Board findBoard = boardService.findBoard(boardId);

        Like like = Like.builder().member(findMember).board(findBoard).build();

        return likeRepository.save(like);
    }

    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
