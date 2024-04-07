package com.likelion.shopchoyu.Controller;

import  com.likelion.shopchoyu.Dto.CreateOrderRequestDto;
import  com.likelion.shopchoyu.Dto.UpdateOrderRequestDto;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j //로그 출력을 도와주는 어노테이션
@RestController
@RequestMapping("/order") // uri가 ~/order로 시작하는 요청을 받습니다.
public class OrderController {

    // 1. 주문을 생성하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 생성하기"입니다. -> 주문은 리스트 형태로 요청을 보내주세요!
    @PostMapping("")
    public String createOrder(@RequestBody List<CreateOrderRequestDto> orderList){
        return "주문 생성하기";
    }

    // 2. 주문을 가져오는 컨트롤러를 만듭니다. 이때 return 값은 "주문 가져오기"입니다.
    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable long orderId){
        return "주문 가져오기";
    }

    // 3. 주문을 수정하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 수정하기"입니다.
    @PutMapping("")
    public String updateOrder(@RequestBody UpdateOrderRequestDto orderUpdate){
        return "주문 수정하기";
    }

    // 4. 주문을 삭제하는 컨트롤러를 만듭니다. 이때 return 값은 "주문 삭제하기"입니다.
    @DeleteMapping("")
    public String DeleteOrder(@RequestParam("id") long orderId){
        return "주문 삭제하기";
    }
}