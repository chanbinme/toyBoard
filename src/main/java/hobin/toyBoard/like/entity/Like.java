package hobin.toyBoard.like.entity;

import hobin.toyBoard.audit.Auditable;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.post.entity.Post;
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
    private Post post;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
