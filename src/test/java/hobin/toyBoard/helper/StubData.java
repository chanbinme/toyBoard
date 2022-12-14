package hobin.toyBoard.helper;

import hobin.toyBoard.member.dto.MemberDto;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class StubData {

    private static Map<HttpMethod, Object> stubRequestBody;
    static {
        stubRequestBody = new HashMap<>();
        stubRequestBody.put(HttpMethod.POST, MemberDto.Post.builder()
                .name("홍길동").email("gksmfcksqls@gmail.com").password("Hongildong1234!")
                .nickname("의적홍길동").city("서울시 강북구").street("수유동").zipcode("722-9"));

        stubRequestBody.put(HttpMethod.PATCH, MemberDto.Post.builder()
                .name("김찬빈").password("Kimchanbin1234!").nickname("수유동불주먹").city("의정부시")
                .street("회룡동").zipcode("7777-7777"));
    }
}
