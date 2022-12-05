package hobin.toyBoard.board.service;

import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Board board) {
        Board findBoard = verifiedBoard(board.getBoardId());

        return boardRepository.save(findBoard);
    }

    public Board findBoard(Long boardId) {
        return verifiedBoard(boardId);
    }

    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page - 1, size, Sort.by("boardId").descending()));
    }

    @Transactional
    public void deleteBoard(Board board) {
        Board findBoard = verifiedBoard(board.getBoardId());
        boardRepository.delete(findBoard);
    }

    public Board verifiedBoard(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard
                .orElseThrow(() -> new RuntimeException("Board를 찾을 수 없습니다."));

        return findBoard;
    }
}
