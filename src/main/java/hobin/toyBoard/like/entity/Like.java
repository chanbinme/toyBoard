package hobin.toyBoard.like.entity;

import hobin.toyBoard.audit.Auditable;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Like extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Board board;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
