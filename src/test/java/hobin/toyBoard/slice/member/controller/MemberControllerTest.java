package hobin.toyBoard.slice.member.controller;

import com.google.gson.Gson;
import hobin.toyBoard.member.controller.MemberController;
import hobin.toyBoard.member.dto.MemberDto;
import hobin.toyBoard.member.mapper.MemberMapper;
import hobin.toyBoard.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;
    
    @Test
    public void postMemberTest() throws Exception {
        // given
        MemberDto.Post post;
        post = MemberDto.Post.builder().name("홍길동").email("gksmfcksqls@gmail.com").build();

        // when
        
        // then
        
    }
        
}