package com.likelion.shopchoyu.Dto;

import com.likelion.shopchoyu.Entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //매개변수 없는 생성자를 생성해 줍니다.
@AllArgsConstructor //모든 매개변수를 받는 생성자를 생성해 줍니다.
@Getter
public class CreateOrderRequestDto {

    //상품 이름
    public String name;

    //수량
    public int quantity;

    //가격
    public int price;

    //클래스의 인스턴스 변수들을 사용하여 Order 엔티티의 인스턴스를 생성하고 반환하는 역할
    //생성(Create) 작업에서는 toEntity() 메서드를 통해 DTO의 모든 정보를 새 엔티티 객체에 매핑
    public Order toEntity(){
        return Order.builder()
                .name(name)
                .quantity(quantity)
                .price(price)
                .build();
    }
}