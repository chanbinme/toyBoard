package hobin.toyBoard.board.dto;

import hobin.toyBoard.board.entity.Board;

import hobin.toyBoard.photo.entity.Photo;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
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
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long boardId;
        private String writer;
        private String title;
        private String content;
        private List<Long> photoIds;
        private int commentsCount;
        private int likesCount;


        @Builder
        public Response(Board board) {
            this.boardId = board.getBoardId();
            this.writer = board.getMember().getNickname();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.photoIds = board.getPhotos().stream()
                    .map(Photo::getPhotoId).collect(Collectors.toList());
            this.commentsCount = board.getComments() == null ? 0 : board.getComments().size();
            this.likesCount = board.getLikes() == null ? 0 : board.getLikes().size();
        }
    }
}
