package hobin.toyBoard.comment.dto;

import hobin.toyBoard.comment.entity.Comment;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class CommentDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Post {
        @NotBlank(message = "댓글이 공백이 아니어야 합니다.")
        private String content;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch {
        @NotBlank(message = "댓글이 공백이 아니어야 합니다.")
        private String content;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long commentId;
        private String writer;
        private String content;

        @Builder
        public Response(Comment comment) {
            this.commentId = comment.getCommentId();
            this.writer = comment.getMember().getNickname();
            this.content = comment.getContent();
        }
    }
}
