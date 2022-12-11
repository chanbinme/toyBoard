package hobin.toyBoard.comment.entity;

import hobin.toyBoard.audit.BaseTimeEntity;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.board.entity.Board;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 800, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(nullable = false)
    private String writer;

    public void changeContent(String content) {
        this.content = content;
    }

    public void createComment(Member member, Board board) {
        this.member = member;
        this.board = board;
        writer = member.getNickname();
    }
}
