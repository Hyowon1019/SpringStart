package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository(); // X -> 객체 꼬일 가능성

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public long join(Member member) {
        // 같은 이름이 있는 중복 회원X
//        Optional<Member> result = memberRepository.findByName(member.getName()); --> 코드가 이쁘지 않으므로 아래처럼 수정
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

//        long start = System.currentTimeMillis();
//
//        try {
            ValidateDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member);
            return member.getId();
//        }finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish- start;
//            System.out.println("join = " + timeMs + "ms");
//        }
    }

    public List<Member> findMembers() {

//        long start = System.currentTimeMillis();

//        try {
            return memberRepository.findAll();
//        }finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish- start;
//            System.out.println("findMembers = " + timeMs + "ms");
//        }
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    private void ValidateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
