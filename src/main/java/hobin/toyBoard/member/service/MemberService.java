package hobin.toyBoard.member.service;

import hobin.toyBoard.exception.BussinessLogicException;
import hobin.toyBoard.exception.ExceptionCode;
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
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
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

    @Transactional
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());
        findMember.changeMember(member);
        return findMember;
    }

    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.delete(findVerifiedMember(memberId));
    }

    public void verifyExistMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            throw new BussinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    public Member findVerifiedMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BussinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
