package com.likelion.shopchoyu.Dto;

import com.likelion.shopchoyu.Entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;

    //상품 이름
    public String name;

    //수량
    public int quantity;

    //가격
    public int price;

    //Order 엔티티 객체를 매개변수로 받아, 해당 엔티티의 데이터를 기반으로 OrderResponseDto 객체를 생성하고 반환하는 정적 메서드
    public static OrderResponseDto from(Order order){
        return OrderResponseDto.builder()
                .id(order.getId())
                .name(order.getName())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .build();
    }

}
