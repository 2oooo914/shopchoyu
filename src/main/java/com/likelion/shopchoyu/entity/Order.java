package com.likelion.shopchoyu.entity;

import com.likelion.shopchoyu.dto.request.UpdateOrderRequestDto;
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
    @Column(name = "orderId")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    //Order : User = N : 1
    //주문과 사용자 사이의 N:1 관계를 설정하고, 필요할 때만 관련 사용자 정보를 불러옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    //제어와 유지보수에 용이하기 위해 Setter를 사용하지 않는다
    public void update(UpdateOrderRequestDto orderRequestDto) { // Order Entity의 정보를 업데이트
        name = orderRequestDto.getName();
        quantity = orderRequestDto.getQuantity();
        price = orderRequestDto.getPrice();
    } //Dto 값을 Order Entity의 해당 필드에 할당 -> 새로운 값으로 업데이트

    //주문에 사용자 할당, 관계를 설정한다
    public void setUser(User user){
        this.user = user;
    }
}