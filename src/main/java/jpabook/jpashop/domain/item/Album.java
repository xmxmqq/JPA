package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") // @DiscriminatorColumn으로 정해주었던 컬럼(dtype)에 어떤 값이 들어갈지를 정해준다.
@Getter
@Setter
public class Album extends Item {
    private String artist;
    private String etc;
}
