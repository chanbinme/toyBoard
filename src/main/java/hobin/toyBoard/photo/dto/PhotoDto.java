package hobin.toyBoard.photo.dto;

import hobin.toyBoard.board.entity.Board;
import lombok.*;

import java.io.File;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoDto {
    private String origFileName;
    private String filePath;
    private Long fileSize;
}
