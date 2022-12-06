package hobin.toyBoard.comment.entity;

import hobin.toyBoard.audit.Auditable;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.board.entity.Board;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Auditable {

    @Id
    @GeneratedValue
    private Long commentId;

    @Column(length = 100, nullable = false)
    private String content;

    // Member의 nickname과 같은 역할
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void changeContent(String content) {
        this.content = content;
    }
}
