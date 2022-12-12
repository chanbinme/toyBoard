package hobin.toyBoard.member.dto;

import hobin.toyBoard.member.entity.Address;
import hobin.toyBoard.member.entity.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Post {
        @NotBlank(message = "아이디를 입력해주세요.")
        private String name;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;

        @NotBlank(message = "주소를 입력해주세요.")
        private String city;

        @NotBlank(message = "주소를 입력해주세요.")
        private String street;

        @NotBlank(message = "주소를 입력해주세요.")
        private String zipcode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch {
        private Long memberId;

        private String name;

        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;

        private Member.MemberStatus memberStatus;

        private String city;

        private String street;

        private String zipcode;

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long memberId;

        private String name;

        private String email;

        private String password;

        private String nickname;

        private String memberStatus;

        private Address address;

        private int uploadedBoard;

        private int allCommentsCount;

        private int allLikesCount;

        @Builder
        public Response(Member member) {
            this.memberId = member.getMemberId();
            this.name = member.getName();
            this.email = member.getEmail();
            this.password = member.getPassword();
            this.nickname = member.getNickname();
            this.memberStatus = member.getMemberStatus().getStatus();
            this.address = member.getAddress();
            this.uploadedBoard = member.getBoards() == null ? 0 : member.getBoards().size();
            this.allCommentsCount = member.getComments() == null ? 0 : member.getComments().size();
            this.allLikesCount = member.getLikes() == null ? 0 : member.getLikes().size();
        }
    }
}
