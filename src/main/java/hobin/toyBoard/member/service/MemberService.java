package hobin.toyBoard.member.service;

import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

//    @Transactional
    public Member createMember(Member member) {
        verifyExistMember(member.getEmail());
        return memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findALl(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page - 1, size,
                Sort.by("memberId").descending()));
    }

//    @Transactional
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());
        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.changeName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.changePassword(password));
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickname -> findMember.changeNickname(nickname));
//        Optional.ofNullable(member.getMemberStatus())
//                .ifPresent(memberStatus -> findMember.changeStatus(memberStatus));
        Optional.ofNullable(member.getAddress().getCity())
                .ifPresent(city -> findMember.changeAddressCity(city));
        Optional.ofNullable(member.getAddress().getStreet())
                .ifPresent(street -> findMember.changeAddressStreet(street));
        Optional.ofNullable(member.getAddress().getZipcode())
                .ifPresent(zipcode -> findMember.changeAddressZipcode(zipcode));

        return findMember;
    }

//    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.delete(findVerifiedMember(memberId));
    }

    public void verifyExistMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    public Member findVerifiedMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
