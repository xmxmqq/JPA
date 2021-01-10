package jpabook.jpashop.domain;

import com.sun.xml.txw2.IllegalSignatureException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // FK
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    // 1:1 관계에서는 FK를 두는 곳을 선택할 수 있음
    // 주로 많이 이용하는 테이블에 FK를 둠
    // 주로 Order에서 Delivery를 조회하기 때문에, 연관관계의 주인(FK와 가까운 쪽)을 Order로 둔다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 주문시간
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // 형은 String
    private OrderStatus status; // 주문 상태 [ORDER, CANCLE]



    /*
    생성 메서드
    */
    // 복잡한 엔티티의 경우 보통 엔티티 내부에서 엔티티 생성을 쉽게할 수 있는 메서드를 만들어 주기도 한다.
    // 새로운 Order객체 생성 후 Member와 Delivery 객체를 받아 넣어주고, 여러 OrderItem을 받아서 ArrayList에 추가해준다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        // 실무에서는 Ordertem이 DTO를 타고 오거나, 이 생성 메서드 내부에서 생성될 수도 있다.
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER); // OrderStatus.ORDER로 초기화
        order.setOrderDate(LocalDateTime.now()); // 현재시간으로 초기화
        return order;
    }

    /*
    비즈니스 로직
    */
    // 주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalSignatureException("not allowed to cancle the items already delivered");
        }
        // 배송이 완료되지 않았다면 주문의 상태를 cancel로 초기화
        this.setStatus(OrderStatus.CANCLE);

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 재고 수량을 다시 돌리는 역할
        }
    }

    // 전체 주문가겨 조회
    public int getTotalPrice() {

        // 리스트를 .stream()으로 변환 -> mapToInt 메서드 내부의 콜백함수로 OrderItem::getTotalPrice -> .sum()으로 전부 합산
        return this.orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }


//    //==연관관계 (편의)메서드==///
//    // 주체적으로 관계를 컨트롤하는 클래스에 넣으면 좋다.
//    public void setMember(Member member) {
//        this.member = member;
//        member.getOrders().add(this);
//    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
//
//    public void setDelivery(Delivery delivery) {
//        this.delivery = delivery;
//        delivery.setOrder(this);
//    }
}