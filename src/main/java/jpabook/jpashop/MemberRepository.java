package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 객체의 타입에 따라 EntityManager가 다른 방식으로 작동(비영속, 준영속, 영)
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    // CQS 원칙 중 쿼리의 의미이기 때문에, 객체를 그대로 반환
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
