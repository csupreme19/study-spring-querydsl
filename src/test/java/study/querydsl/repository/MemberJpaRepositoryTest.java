package study.querydsl.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

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

    @Test
    void searchTest() {
        Team teamA = new Team("teamA");
        em.persist(teamA);
        Team teamB = new Team("teamB");
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> list = memberJpaRepository.searchByBuilder(condition);
        List<MemberTeamDto> list2 = memberJpaRepository.searchByWhereParameter(condition);

        assertThat(list).extracting("username").containsExactly("member4");
        assertThat(list2).extracting("username").containsExactly("member4");
    }

}