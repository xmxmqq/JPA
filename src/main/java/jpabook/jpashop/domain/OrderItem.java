package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // OrderItem은 하나의 Item을 가짐, Item은 많은 OrderItem을 가짐

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; //주문 수량


    /*
    생성 메서드
    */
    // 주문상품 생성
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }


    /*
   비즈니스 로직 추가
    */
    // 주문상품 취소
    public void cancel() {
        this.getItem().addStock(count); // 재고수량 초기화(원상복귀)
    }

    // 주문상품 총 가격
    public int getTotalPrice() {
    return this.getOrderPrice() * this.getCount();
    }



}
