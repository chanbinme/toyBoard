package hobin.toyBoard.member.controller;


import hobin.toyBoard.member.dto.MemberDto;
import hobin.toyBoard.member.entity.Member;
import hobin.toyBoard.member.mapper.MemberMapper;
import hobin.toyBoard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
@Validated
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberPostDto) {
        Member member = mapper.memberPostDtoToMember(memberPostDto);
        member.createAddress(memberPostDto.getCity(), memberPostDto.getStreet(), memberPostDto.getZipcode());
        member.changeStatus(Member.MemberStatus.MEMBER_ACTIVE);
        Member saveMember = memberService.createMember(member);

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(saveMember), HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive Long memberId,
                                      @Valid @RequestBody MemberDto.Patch memberPatchDto) {
        memberPatchDto.setMemberId(memberId);
        Member member = mapper.memberPatchDtoToMember(memberPatchDto);
        member.createAddress(memberPatchDto.getCity(), memberPatchDto.getStreet(), memberPatchDto.getZipcode());


        Member updateMember = memberService.updateMember(member);

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(updateMember), HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive Long memberId) {
        Member findMember = memberService.findMember(memberId);

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(findMember), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam("page") @Positive int page,
                                     @RequestParam("size") @Positive int size) {
        List<Member> findMembers = memberService.findALl(page, size).getContent();
        List<MemberDto.Response> responses = mapper.membersToMemberResponseDtos(findMembers);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
