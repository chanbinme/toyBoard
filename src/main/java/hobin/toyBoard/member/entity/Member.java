package hobin.toyBoard.member.entity;

import hobin.toyBoard.audit.Auditable;
import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.like.entity.Like;
import hobin.toyBoard.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, updatable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "post")
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "like")
    private List<Like> likes = new ArrayList<>();
    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>();

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
