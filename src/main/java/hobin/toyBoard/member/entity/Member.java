package hobin.toyBoard.member.entity;

import hobin.toyBoard.audit.BaseTimeEntity;
import hobin.toyBoard.comment.entity.Comment;
import hobin.toyBoard.like.entity.Like;
import hobin.toyBoard.board.entity.Board;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addBoard(Board board) {
        this.boards.add(board);
    }

    public void changeStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public void createAddress(String city, String street, String zipcode) {
        Address address = new Address();
        address.changeCity(city);
        address.changeStreet(street);
        address.changeZipcode(zipcode);
        this.address = address;
    }

    public void changeMember(Member member) {
        Optional.ofNullable(member.getName())
                .ifPresent(name -> this.name = name);
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> this.password = password);
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickname -> this.nickname = nickname);
        Optional.ofNullable(member.getMemberStatus())
                        .ifPresent(status -> this.memberStatus = status);
        Optional.ofNullable(member.getAddress().getCity())
                .ifPresent(this.address::changeCity);
        Optional.ofNullable(member.getAddress().getStreet())
                .ifPresent(this.address::changeStreet);
        Optional.ofNullable(member.getAddress().getZipcode())
                .ifPresent(this.address::changeZipcode);
    }

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
