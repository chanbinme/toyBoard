package hobin.toyBoard.board.service;

import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    public void createPostTest() throws Exception {
        // given

        // when

        // then


    }

}