package hobin.toyBoard.board.controller;

import com.google.gson.Gson;
import hobin.toyBoard.board.mapper.BoardMapper;
import hobin.toyBoard.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostControllerTest.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardMapper mapper;

    @Test
    public void () throws Exception {
        // given

        // when

        // then

    }

}