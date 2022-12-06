package hobin.toyBoard.board.controller;

import hobin.toyBoard.board.dto.BoardDto;
import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.mapper.BoardMapper;
import hobin.toyBoard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardMapper mapper;
    private final BoardService boardService;


    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post boardPostDto) {
        Board saveBoard = boardService.createBoard(mapper.boardPostToBoard(boardPostDto));
        return new ResponseEntity<>(mapper.boardToBoardResponse(saveBoard), HttpStatus.CREATED);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("boardId") @Positive Long boardId) {
        Board findBoard = boardService.findBoard(boardId);

        return new ResponseEntity<>(mapper.boardToBoardResponse(findBoard), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBoards(@PathVariable("page") @Positive int page,
                                   @PathVariable("size") @Positive int size) {
        List<Board> findBoards = boardService.findBoards(page, size).getContent();

        return new ResponseEntity<>(mapper.boardsToBoardResponses(findBoards), HttpStatus.OK);
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("boardId") @Positive Long boardId,
                          @Valid @RequestBody BoardDto.Patch boardPatchDto) {
        boardPatchDto.setBoardId(boardId);
        Board saveBoard = boardService.updateBoard(mapper.boardPatchToBoard(boardPatchDto));

        return new ResponseEntity<>(mapper.boardToBoardResponse(saveBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("boardId") @Positive Long boardId) {
        boardService.deleteBoard(boardService.findBoard(boardId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
