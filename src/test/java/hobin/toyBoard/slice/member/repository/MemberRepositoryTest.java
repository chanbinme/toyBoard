package hobin.toyBoard.slice.member.repository;

import hobin.toyBoard.member.entity.Address;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.member.repository.MemberRepository;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원_저장_불러오기() throws Exception {
        // given
        String name = "홍길동";
        String email = "gksmfcksqls@gmail.com";
        String password = "Hongildong1234!";
        String nickname = "의적홍길동";
        String city = "서울시 강북구";
        String street = "수유동";
        String zipcode = "722-9";
        Member.MemberStatus status = Member.MemberStatus.MEMBER_ACTIVE;

        Address address = Address.builder().city(city).street(street).zipcode(zipcode).build();
        Member member = Member.builder()
                .name(name).email(email).password(password)
                .nickname(nickname).memberStatus(status).address(address).build();
        
        // when
        memberRepository.save(member);
        List<Member> findAll = memberRepository.findAll();

        // then
        Member findMember = findAll.get(0);
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(findMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(findMember.getMemberStatus().getStatus()).isEqualTo(member.getMemberStatus().getStatus());
        assertThat(findMember.getAddress().getCity()).isEqualTo(member.getAddress().getCity());
        assertThat(findMember.getAddress().getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(member.getAddress().getZipcode());
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        String name = "홍길동";
        String email = "gksmfcksqls@gmail.com";
        String password = "Hongildong1234!";
        String nickname = "의적홍길동";
        String city = "서울시 강북구";
        String street = "수유동";
        String zipcode = "722-9";
        Member.MemberStatus status = Member.MemberStatus.MEMBER_ACTIVE;

        LocalDateTime now = LocalDateTime.of(2022, 12, 14, 0, 0, 0);

        Address address = Address.builder().city(city).street(street).zipcode(zipcode).build();
        Member member = Member.builder()
                .name(name).email(email).password(password)
                .nickname(nickname).memberStatus(status).address(address).build();
        memberRepository.save(member);

        //when
        List<Member> findAll = memberRepository.findAll();

        //then
        Member member1 = findAll.get(0);

        System.out.println(">>>>>>> createDate = " + member1.getCreatedAt() + ", lasModifiedDate = " + member1.getLastModifiedAt());

        assertThat(member1.getCreatedAt()).isAfter(now);
        assertThat(member1.getLastModifiedAt()).isAfter(now);
    }
}