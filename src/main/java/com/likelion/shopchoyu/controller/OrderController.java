package com.likelion.shopchoyu.controller;

import com.likelion.shopchoyu.dto.request.CreateOrderRequestDto;
import com.likelion.shopchoyu.dto.response.OrderResponseDto;
import com.likelion.shopchoyu.dto.request.UpdateOrderRequestDto;
import com.likelion.shopchoyu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j //로그 출력을 도와주는 어노테이션
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //UserDetails에서 사용자 이름(이메일)을 가져와서 사용
    // 1. 주문을 생성하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 생성하기"입니다. -> 주문은 리스트 형태로 요청을 보내주세요!
    @PostMapping("")
    public ResponseEntity<List<OrderResponseDto>> createOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<CreateOrderRequestDto> orderList) {
        List<OrderResponseDto> responseDtoList = orderService.createOrder(userDetails.getUsername(), orderList).stream().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDtoList);
    }

    // 2. 주문을 가져오는 컨트롤러를 만듭니다. 이때 return 값은 "주문 가져오기"입니다.
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long orderId) {
        OrderResponseDto responseDto = orderService.getOrder(userDetails.getUsername(), orderId);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("주문을 찾을 수 없습니다.");
        }
    }

    // 3. 주문을 수정하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 수정하기"입니다.
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long orderId, @RequestBody UpdateOrderRequestDto updateOrderRequestDto) {
        OrderResponseDto responseDto = orderService.updateOrder(userDetails.getUsername(), orderId, updateOrderRequestDto);
        if (responseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body("주문 수정 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("주문 수정 실패");
        }
    }

    // 4. 주문을 삭제하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 삭제하기"입니다.
    @DeleteMapping("")
    public ResponseEntity<?> deleteOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("id") Long id) {
        orderService.deleteOrder(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}
