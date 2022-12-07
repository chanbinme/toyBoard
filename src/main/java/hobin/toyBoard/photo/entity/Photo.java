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
@Table(name = "file")
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String origFileName; // 원본 파일명

    @Column(nullable = false)
    private String filepath; // 파일 저장 경로

    private Long fileSize;

    @Builder
    public Photo(String origFileName, String filepath, Long fileSize) {
        this.origFileName = origFileName;
        this.filepath = filepath;
        this.fileSize = fileSize;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
