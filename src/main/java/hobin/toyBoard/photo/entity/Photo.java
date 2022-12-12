package hobin.toyBoard.photo.entity;

import ch.qos.logback.core.util.FileSize;
import hobin.toyBoard.audit.BaseTimeEntity;
import hobin.toyBoard.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHOTO_ID")
    private Long photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(nullable = false)
    private String origFileName; // 원본 파일명

    @Column(nullable = false)
    private String filePath; // 파일 저장 경로

    private Long fileSize;

    @Builder
    public Photo(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void setBoard(Board board) {
        this.board = board;

        if (!board.getPhotos().contains(this))
            board.getPhotos().add(this);
    }
}
