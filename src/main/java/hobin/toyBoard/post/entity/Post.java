package hobin.toyBoard.post.entity;

import hobin.toyBoard.audit.Auditable;
import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "Posts")
public class Post extends Auditable {

    @Id
    @GeneratedValue
    private Long postId;

    @Column(length = 800)
    private String content;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}
