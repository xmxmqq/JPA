package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    // 회원 찾기
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 회원 리스트 불러오기
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    // 해당 이름을 가진 회원 리스트 불러오기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    // 객체의 타입에 따라 EntityManager가 다른 방식으로 작동(비영속, 준영속, 영속)
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    // CQS 원칙 중 쿼리의 의미이기 때문에, 객체를 그대로 반환
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
