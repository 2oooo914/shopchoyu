package com.likelion.shopchoyu.Service;

import com.likelion.shopchoyu.Dto.CreateOrderRequestDto;
import com.likelion.shopchoyu.Dto.UpdateOrderRequestDto;
import com.likelion.shopchoyu.Dto.OrderResponseDto;
import com.likelion.shopchoyu.Entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {

    //CreateOrderRequestDto 리스트를 받아, 각 DTO를 순회하면서 주문 엔티티를 생성하고, 데이터베이스에 저장
    //CreateOrderRequestDto 객체 리스트를 순회하면서 각각의 객체에 대해 toEntity() 메서드를 호출
    public void createOrder(List<CreateOrderRequestDto> createOrderRequestDtoList) {
        createOrderRequestDtoList.forEach(createOrderRequestDto -> {
            Order order = createOrderRequestDto.toEntity();
        });
    }

    public OrderResponseDto getOrder(Long orderId){
        //DB에서 가져오기
        Order order = new Order();
        return OrderResponseDto.from(order);
    }

    public void updateOrder(Long orderId, UpdateOrderRequestDto OrderRequestDto){
        //DB에서 가져오기
        Order order = new Order();
        order.update(OrderRequestDto);
    }
    public void deleteOrder(Long orderID){

    }
}
