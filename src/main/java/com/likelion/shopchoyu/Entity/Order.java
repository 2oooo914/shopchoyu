package com.likelion.shopchoyu.Entity;

import com.likelion.shopchoyu.Dto.UpdateOrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {

    @Id //각각의 객체를 구별
    @GeneratedValue(strategy = GenerationType.AUTO) //1부터 id값 하나씩 증가
    private Long id;

    @Column
    public String name;

    @Column
    public int quantity;

    @Column
    public int price;

    //제어와 유지보수에 용이하기 위해 Setter를 사용하지 않는다
    public void update(UpdateOrderRequestDto orderRequestDto) { // Order Entity의 정보를 업데이트
        name = orderRequestDto.getName();
        quantity = orderRequestDto.getQuantity();
        price = orderRequestDto.getPrice();
    } //Dto 값을 Order Entity의 해당 필드에 할당 -> 새로운 값으로 업데이트
}