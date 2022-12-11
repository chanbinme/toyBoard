package hobin.toyBoard.board.entity;

import hobin.toyBoard.audit.BaseTimeEntity;
import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.like.entity.Like;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.photo.entity.Photo;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 800, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", updatable = false)
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getBoards().contains(this)) {
            this.member.addBoard(this);
        }
    }

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }

    @Builder
    public Board(Long boardId, String title, String content, Member member, List<Comment> comments, List<Like> likes) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.member = member;
        this.comments = comments;
        this.likes = likes;
    }

    // Board에서 파일을 처리하기 위해
    public void addPhoto(Photo photo) {
        this.photos.add(photo);

        if (photo.getBoard() != this){
            photo.setBoard(this);
        }
    }
}
