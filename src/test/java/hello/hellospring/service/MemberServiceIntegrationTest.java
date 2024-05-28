package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

//    MemberService memberService = new MemberService(); // --> X
    @Autowired MemberService memberService;
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository(); // --> X
    @Autowired MemberRepository memberRepository;

    @Test
//    @Commit
    void 회원가입() {
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

}