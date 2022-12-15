package hobin.toyBoard.board.controller;

import hobin.toyBoard.board.dto.BoardDto;
import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.mapper.BoardMapper;
import hobin.toyBoard.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * @RequestBody는 body로 전달받은 JSON 형태의 데이터를 파싱해준다.
     * 반면, Content-Type이 multipart/form-data로 전달되어 올 경우 Exception이 발생한다.
     * 따라서 @RequestBody가 아닌 다른 방식을 통해 데이터를 전달 받아야 한다.
     */
    @PostMapping
    public ResponseEntity postBoard(@RequestParam("memberId") @Positive Long memberId,
                                    @RequestPart(value = "image", required = false) List<MultipartFile> files,
                                    @RequestPart(value = "boardPostDto") @Valid BoardDto.Post boardPostDto) throws Exception {
        Board saveBoard = boardService.createBoard(memberId, mapper.boardPostToBoard(boardPostDto), files);
        return new ResponseEntity<>(mapper.boardToBoardResponse(saveBoard), HttpStatus.CREATED);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive Long boardId) {
        Board findBoard = boardService.findBoard(boardId);
        BoardDto.Response response = mapper.boardToBoardResponse(findBoard);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBoards(@RequestParam("page") @Positive int page,
                                   @RequestParam("size") @Positive int size) {
        List<Board> findAll = boardService.findBoards(page, size).getContent();

        return new ResponseEntity<>(mapper.boardsToBoardResponses(findAll), HttpStatus.OK);
    }

    @GetMapping("/bymember/{member-id}")
    public ResponseEntity getBordsByMember(@PathVariable("member-id") @Positive Long memberId,
                                           @RequestParam("page") @Positive int page,
                                           @RequestParam("size") @Positive int size) {
        List<Board> findAllByMember = boardService.findAllByMember(memberId, page, size).getContent();

        return new ResponseEntity<>(mapper.boardsToBoardResponses(findAllByMember), HttpStatus.OK);
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") @Positive Long boardId,
                                     @RequestPart(value = "image", required = false) List<MultipartFile> files,
                                     @RequestPart(value = "boardPatchDto") @Valid BoardDto.Patch boardPatchDto
                                     ) throws Exception {

        boardPatchDto.setBoardId(boardId);
        Board saveBoard = boardService.updateBoard(mapper.boardPatchToBoard(boardPatchDto), files);
        return new ResponseEntity<>(mapper.boardToBoardResponse(saveBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") @Positive Long boardId) {
        boardService.deleteBoard(boardService.findBoard(boardId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
