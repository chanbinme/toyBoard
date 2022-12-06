package hobin.toyBoard.board.mapper;

import hobin.toyBoard.board.dto.BoardDto;
import hobin.toyBoard.board.entity.Board;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostToBoard(BoardDto.Post boardPostDto);

    Board boardPatchToBoard(BoardDto.Patch boardPatchDto);

    BoardDto.Response boardToBoardResponse(Board board);

    List<BoardDto.Response> boardsToBoardResponses(List<Board> boards);
}
