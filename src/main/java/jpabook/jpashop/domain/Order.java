package jpabook.jpashop.domain;

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

    @ManyToOne
    @JoinColumn(name = "member_id") // FK
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    // 1:1 관계에서는 FK를 두는 곳을 선택할 수 있음
    // 주로 많이 이용하는 테이블에 FK를 둠
    // 여기서는 Order이 많이 이용될 것 같으니
    @JoinColumn(name = "delivery_id") // 주로 Order에서 Delivery를 조회하기 때문에, 연관관계의 주인(FK와 가까운 쪽)을 Order로 둔다.
    private Delivery delivery;

    // 주문시간
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // 형은 String
    private OrderStatus status; // 주문 상태 [ORDER, CANCLE]

//    //==연관관계 (편의)메서드==///
//    // 주체적으로 관계를 컨트롤하는 클래스에 넣으면 좋다.
//    public void setMember(Member member) {
//        this.member = member;
//        member.getOrders().add(this);
//    }
//
//    public void addOrderItem(OrderItem orderItem) {
//        orderItems.add(orderItem);
//        orderItem.setOrder(this);
//    }
//
//    public void setDelivery(Delivery delivery) {
//        this.delivery = delivery;
//        delivery.setOrder(this);
//    }
}