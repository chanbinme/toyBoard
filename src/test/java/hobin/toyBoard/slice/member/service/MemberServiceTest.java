package hobin.toyBoard.slice.member.service;

import hobin.toyBoard.exception.BussinessLogicException;
import hobin.toyBoard.member.entity.Address;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.member.repository.MemberRepository;
import hobin.toyBoard.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void 회원가입_성공() throws Exception {
        // given
        Member member = createMember();

        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.empty());
        given(memberRepository.save(Mockito.any(Member.class))).willReturn(member);

        // when
        Member saveMember = memberService.createMember(member);

        // then
        assertNotNull(saveMember);
        assertThat(saveMember.getName()).isEqualTo(member.getName());
        assertThat(saveMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(saveMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(saveMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(saveMember.getAddress().getCity()).isEqualTo(member.getAddress().getCity());
        assertThat(saveMember.getAddress().getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(saveMember.getAddress().getZipcode()).isEqualTo(member.getAddress().getZipcode());
    }


    @Test
    public void 회원가입_실패() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(member));

        // when / then
        assertThrows(BussinessLogicException.class, () -> memberService.createMember(member));
    }

    @Test
    public void 회원조회_성공() throws Exception {
        // given
        Member member = createMember();

        given(memberRepository.findById(Mockito.anyLong())).willReturn(Optional.of(member));

        // when
        Member findMember = memberService.findMember(member.getMemberId());

        // then
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(findMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(findMember.getAddress().getCity()).isEqualTo(member.getAddress().getCity());
        assertThat(findMember.getAddress().getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(member.getAddress().getZipcode());
    }

    @Test
    public void 회원검증_실패() throws Exception {
        // given
        given(memberRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        // when / then
        assertThrows(BussinessLogicException.class, () -> memberService.findMember(2L));
    }
    
    @Test
    public void 회원삭제_성공() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findById(Mockito.anyLong())).willReturn(Optional.of(member));
        doNothing().when(memberRepository).delete(member);

        // when / then
        memberService.deleteMember(member.getMemberId());
    }

    private Member createMember() {
        Long memberId = 1L;
        String name = "홍길동";
        String email = "gksmfcksqls@gmail.com";
        String password = "Hongildong1234!";
        String nickname = "의적홍길동";
        String city = "서울시 강북구";
        String street = "수유동";
        String zipcode = "722-9";
        Member.MemberStatus status = Member.MemberStatus.MEMBER_ACTIVE;
        Address address = Address.builder().city(city).street(street).zipcode(zipcode).build();

        return Member.builder()
                .memberId(memberId).name(name).email(email).password(password)
                .nickname(nickname).memberStatus(status).address(address).build();
    }
}