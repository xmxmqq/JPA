package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum일 때는, @Enumerated를 반드시 추가
    // 웬만하면 형 타입은 String으로
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
