package study.querydsl.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void basicJpaTest() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> members = memberJpaRepository.findAll();
        assertThat(members).containsExactly(member);

        List<Member> members1 = memberJpaRepository.findByUsername(member.getUsername());
        assertThat(members1).containsExactly(member);
    }

    @Test
    void basicQuerydslTest() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById_Querydsl(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> members = memberJpaRepository.findAll_Querydsl();
        assertThat(members).containsExactly(member);

        List<Member> members1 = memberJpaRepository.findByUsername_Querydsl(member.getUsername());
        assertThat(members1).containsExactly(member);
    }
}