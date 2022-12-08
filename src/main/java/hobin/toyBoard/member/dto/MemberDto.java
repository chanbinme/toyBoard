package hobin.toyBoard.member.dto;

import hobin.toyBoard.member.entity.Address;
import hobin.toyBoard.member.entity.Member;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;

//        @NotBlank(message = "회원 상태를 입력해주세요.")
//        private Member.MemberStatus memberStatus = Member.MemberStatus.MEMBER_ACTIVE;
//
//        @NotBlank(message = "주소를 입력해주세요.")
//        private Address address;
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

        private Address address;

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long memberId;

        private String name;

        private String email;

        private String password;

        private String nickname;

        private Member.MemberStatus memberStatus;

        private Address address;

    }
}
