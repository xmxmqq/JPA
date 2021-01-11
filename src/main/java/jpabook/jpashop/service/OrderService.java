package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true) // 조회성능 최적
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final EntityManager em;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회(누가 무엇을)
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        // 이렇게 생성 메서드가 있는 경우에는 기본 생성은 막는 것이 좋다.
        // new OrderItem()과 같은 식으로 생성하다보면 나중에 소스코드가 퍼졌을 때 관리하기 어려울 수 있다.
        // 생성 메서드를 막을 때는 롬복의 @NoArgusConstructor(access = AccessLevel.PROTECTED) 어노테이션이 유용
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
//        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        // Cascade 옵션 때문에 저장을 한 번만 해도 된다.
        // OrderItem과 Delivery 엔티티에 대해서도 save가 적용된다.
        // 참조하는 주인이 private owner일 때만 사용하는 것이 좋다.
        // 만일 다른 테이블에서 OrderItem이나 Delivery를 참조한다면, 다른 테이블에서 참조하는 엔티티가 변할 수도 있기에 주의해야 한다.
        orderRepository.save(order);
        return order.getId();
    }

//    public List<Order> findByCriteria(OrderSearch orderSearch) {
//
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery<Order> criteriaBuilderQuery = criteriaBuilder.createQuery(Order.class);
//        Root<Order> orderRoot = criteriaBuilderQuery.from(Order.class);
//        Join<Object, Object> memberJoin = orderRoot.join("member", JoinType.INNER);
//
//        // 여기서 동적 쿼리에 대한 커넥션 조합을 깔끔하게 만들 수 있다.
//        List<Predicate> criteria = new ArrayList<>();
//
//        // 주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            Predicate status = criteriaBuilder.equal(orderRoot.get("status"), orderSearch.getOrderStatus());
//            criteria.add(status);
//        }
//
//        // 회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            Predicate name = criteriaBuilder.like(memberJoin.get("name"), "%" + orderSearch.getMemberName() + "%");
//            criteria.add(name);
//        }
//
//        criteriaBuilderQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[criteria.size()])));
//        TypedQuery<Order> query = em.createQuery(criteriaBuilderQuery).setMaxResults(1000);
//        return query.getResultList();
//    }


}
