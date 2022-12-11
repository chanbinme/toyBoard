package hobin.toyBoard.board.dto;

import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Post {

        @NotBlank(message = "제목을 입력하세요.")
        private String title;

        @NotBlank(message = "내용을 입력하세요")
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch {
        private Long boardId;
        private String title;
        private String content;

        public void setBoardId(Long boardId) {
            this.boardId = boardId;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {

        private Long boardId;
        private String title;
        private String content;
    }
}
