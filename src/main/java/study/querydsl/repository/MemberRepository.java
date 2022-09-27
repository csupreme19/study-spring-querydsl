package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.querydsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    // Spring Data JPA가 JPQL 자동 생성
    // select m from Member m where m.username = :username
    List<Member> findByUsername(String username);

}