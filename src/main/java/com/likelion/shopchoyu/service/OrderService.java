package com.likelion.shopchoyu.service;

import com.likelion.shopchoyu.dto.request.CreateOrderRequestDto;
import com.likelion.shopchoyu.dto.request.UpdateOrderRequestDto;
import com.likelion.shopchoyu.dto.response.OrderResponseDto;
import com.likelion.shopchoyu.entity.Order;
import com.likelion.shopchoyu.entity.User;
import com.likelion.shopchoyu.repository.OrderRepository;
import com.likelion.shopchoyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class OrderService {
    // 의존성 주입을 위한 final 필드 선언
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    //CreateOrderRequestDto 리스트를 받아, 각 DTO를 순회하면서 주문 엔티티를 생성하고, 데이터베이스에 저장
    //CreateOrderRequestDto 객체 리스트를 순회하면서 각각의 객체에 대해 toEntity() 메서드를 호출

    @Transactional
    public List<OrderResponseDto> createOrder(String email, List<CreateOrderRequestDto> createOrderRequestDtoList) {
        List<OrderResponseDto> result = new ArrayList<>();
        createOrderRequestDtoList.forEach(createOrderRequestDto -> {
            Order order = createOrderRequestDto.toEntity();
            Optional<User> userId = userRepository.findById(createOrderRequestDto.getUser().getId());
            if (userId.isPresent()) {
                User findUser = userId.get();
                if (findUser.getEmail().equals(email)) {
                    order.setUser(findUser);
                    Order savedOrder = orderRepository.save(order);
                    result.add(OrderResponseDto.from(savedOrder)); // 저장된 엔티티를 기반으로 OrderResponseDto 생성
                }
            }
        });
        return result;
    }

    public OrderResponseDto getOrder(String email, Long orderId) {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) { //주문이 존재 하면
                Order findOrder = order.get();
                return OrderResponseDto.from(findOrder); //찾은 주문을 OrderResponseDto로 변환해서 반환
            } else {
                // 주문이 없을 경우
                log.info("주문이 존재하지 않음: {}", orderId);
                return null;
            }
        } catch (Exception e) {
            log.error("{} 주문 가져오기 실패", orderId, e);
            return null;
        }
    }

    @Transactional //데이터 변경이 일어나므로, 클래스 레벨에서 설정한 readOnly=true를 오버라이드
    public OrderResponseDto updateOrder(String email, Long orderId, UpdateOrderRequestDto updateOrderRequestDto) {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) { //주문이 존재 하면
                Order findOrder = order.get();
                findOrder.update(updateOrderRequestDto);
                Order updatedOrder = orderRepository.save(findOrder);
                return OrderResponseDto.from(updatedOrder); // 수정된 주문을 반환
            } else {
                // 주문이 없을 경우
                log.info("{}의 주문이 존재하지 않음", orderId);
                return null;
            }
        } catch (Exception e) {
            log.error("{}의 주문 가져오기 실패", orderId, e);
            return null;
        }
    }

    @Transactional
    public void deleteOrder(String email, Long orderId) {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                Order findOrder = order.get();
                if (findOrder.getUser().getEmail().equals(email)) { //order의 User와 login한 User가 같은지 비교함
                    orderRepository.deleteById(orderId);
                }
            }
        } catch (Exception e) {
            log.error("[Order Service] 해당 Id 주문 삭제 실패 ---> {}", orderId);
        }
    }
}
