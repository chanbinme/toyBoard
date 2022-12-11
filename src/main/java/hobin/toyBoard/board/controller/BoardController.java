package hobin.toyBoard.board.controller;

import hobin.toyBoard.board.dto.BoardDto;
import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.mapper.BoardMapper;
import hobin.toyBoard.board.service.BoardService;
import hobin.toyBoard.photo.dto.PhotoDto;
import hobin.toyBoard.photo.entity.Photo;
import hobin.toyBoard.photo.mapper.PhotoMapper;
import hobin.toyBoard.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BoardController {

    private final BoardMapper mapper;
    private final BoardService boardService;
    private final PhotoService photoService;


    /**
     * @RequestBody는 body로 전달받은 JSON 형태의 데이터를 파싱해준다.
     * 반면, Content-Type이 multipart/form-data로 전달되어 올 경우 Exception이 발생한다.
     * 따라서 @RequestBody가 아닌 다른 방식을 통해 데이터를 전달 받아야 한다.
     */
    @PostMapping
    public ResponseEntity postBoard(@RequestParam("memberId") @Positive Long memberId,
                                    @RequestPart(value = "image", required = false) List<MultipartFile> files,
                                    @RequestPart(value = "boardPostDto") BoardDto.Post boardPostDto) throws Exception {
        Board saveBoard = boardService.createBoard(memberId, mapper.boardPostToBoard(boardPostDto), files);
        return new ResponseEntity<>(mapper.boardToBoardResponse(saveBoard), HttpStatus.CREATED);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive Long boardId) {
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
    public ResponseEntity patchBoard(@PathVariable("board-id") @Positive Long boardId,
                          @Valid @RequestBody BoardDto.Patch boardPatchDto) {

        boardPatchDto.setBoardId(boardId);
        Board saveBoard = boardService.updateBoard(mapper.boardPatchToBoard(boardPatchDto));
        return new ResponseEntity<>(mapper.boardToBoardResponse(saveBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") @Positive Long boardId) {
        boardService.deleteBoard(boardService.findBoard(boardId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
