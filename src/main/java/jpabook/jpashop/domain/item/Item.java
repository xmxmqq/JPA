package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // 상속 관계 매핑 전략 (클래스 계층 당 하나의 단일 테이블)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
// 상속 부모 클래스
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 다대다 관계가 가능한 객체와 달리 DB는 불가능하기 때문에 중간 연결 테이블이 필요
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();
}
