package jpabook.jpashop.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 검색기능은 동적쿼리가 필요하기 때문에 나중에 설명
//    public List<Order> findAll(OrderSearch orderSearch) {
//    }
}
