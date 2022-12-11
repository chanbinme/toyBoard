package hobin.toyBoard.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

public class PhotoDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {
        private String origFileName;
        private String filePath;
        private Long fileSize;
    }

    public static class Response {
    }
}
