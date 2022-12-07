package hobin.toyBoard.photo.dto;

import lombok.Builder;

import java.io.File;

public class PhotoDto {

    @Builder
    public static class Post {
        private String origFileName;
        private String filePath;
        private Long fileSize;
    }

    public static class Response {
    }
}
