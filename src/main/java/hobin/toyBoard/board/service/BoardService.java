package hobin.toyBoard.board.service;

import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.repository.BoardRepository;
import hobin.toyBoard.exception.BussinessLogicException;
import hobin.toyBoard.exception.ExceptionCode;
import hobin.toyBoard.member.service.MemberService;
import hobin.toyBoard.photo.entity.Photo;
import hobin.toyBoard.photo.handler.FileHandler;
import hobin.toyBoard.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final PhotoRepository photoRepository;
    private final MemberService memberService;
    private final FileHandler fileHandler;

    @Transactional
    public Board createBoard(@Positive Long memberId, Board board, List<MultipartFile> files)throws Exception {
        board.addMember(memberService.findMember(memberId));
        List<Photo> photos = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if (!photos.isEmpty()) {
            for (Photo photo : photos) {
                // 파일을 DB에 저장
                board.addPhoto(photoRepository.save(photo));
            }
        }
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Board board) {
        Board findBoard = findVerifiedBoard(board.getBoardId());

        Optional.ofNullable(board.getTitle())
                        .ifPresent(title -> findBoard.changeTitle(title));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> findBoard.changeContent(content));

        return boardRepository.save(findBoard);
    }

    public Board findBoard(Long boardId) {
        return findVerifiedBoard(boardId);
    }

    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page - 1, size, Sort.by("boardId").descending()));
    }

    @Transactional
    public void deleteBoard(Board board) {
        Board findBoard = findVerifiedBoard(board.getBoardId());
        boardRepository.delete(findBoard);
    }

    public Board findVerifiedBoard(Long boardId) {

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BussinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }
}
