package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

//    MemberService memberService = new MemberService(); // --> X
    MemberService memberService;
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository(); // --> X
    MemoryMemberRepository memberRepository;
//    BeforeEach 추가

    @BeforeEach
    public void BeforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        /*
        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123"); // --> Error
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // --> Success
        }
        */
//        assertThrows(NullPointerException.class, () -> memberService.join(member2)); // --> Error
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// --> Success
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 같은 메시지가 출력됨을 검증.

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}