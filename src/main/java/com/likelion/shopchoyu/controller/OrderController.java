package com.likelion.shopchoyu.controller;

import com.likelion.shopchoyu.dto.request.CreateOrderRequestDto;
import com.likelion.shopchoyu.dto.response.OrderResponseDto;
import com.likelion.shopchoyu.dto.request.UpdateOrderRequestDto;
import com.likelion.shopchoyu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j //로그 출력을 도와주는 어노테이션
@RestController
@RequestMapping("/order") // uri가 ~/order로 시작하는 요청을 받습니다.
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 1. 주문을 생성하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 생성하기"입니다. -> 주문은 리스트 형태로 요청을 보내주세요!
    @PostMapping("")
    public ResponseEntity<List<OrderResponseDto>> createOrder(@RequestBody List<CreateOrderRequestDto> orderList){
        List<OrderResponseDto> responseDto = orderService.createOrder(orderList);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 2. 주문을 가져오는 컨트롤러를 만듭니다. 이때 return 값은 "주문 가져오기"입니다.
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId){
        OrderResponseDto responseDto = orderService.getOrder(orderId);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("주문을 찾을 수 없습니다.");
        }
    }

    // 3. 주문을 수정하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 수정하기"입니다.
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable long orderId, @RequestBody UpdateOrderRequestDto orderUpdate){
        OrderResponseDto responseDto = orderService.updateOrder(orderId, orderUpdate);
        if (responseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body("주문 수정 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("주문 수정 실패");
        }
    }

    // 4. 주문을 삭제하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 삭제하기"입니다.
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> DeleteOrder(@RequestParam long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}