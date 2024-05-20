package com.likelion.shopchoyu.controller;

import com.likelion.shopchoyu.dto.request.CreateUserRequestDto;
import com.likelion.shopchoyu.dto.request.UpdateUserRequestDto;
import com.likelion.shopchoyu.dto.response.UserResponseDto;
import com.likelion.shopchoyu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j //로그 출력을 도와주는 어노테이션
@RestController
@RequestMapping("/user") // uri가 ~/user로 시작하는 요청을 받습니다.
public class UserController {
    @Autowired
    private UserService userService;

    // 1. 사용자를 생성하는 컨트롤러를 만듭니다. return 값은 "사용자 생성"입니다.
    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        UserResponseDto responseDto = userService.createUser(createUserRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 2. 사용자를 조회하는 컨트롤러를 만듭니다. return 값은 "사용자 조회"입니다.
    @GetMapping("")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails){
        log.info("User Email ---> {}", userDetails.getUsername());
        return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
    }

    // 3. 사용자를 수정하는 컨트롤러를 만듭니다. return 값은 "사용자 수정"입니다.
    @PutMapping("")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDetails userDetails, UpdateUserRequestDto userUpdate){
        UserResponseDto responseDto = userService.updateUser(userDetails.getUsername(), userUpdate);
        if (responseDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 수정 실패");
        }
    }

    // 4. 사용자를 삭제하는 컨트롤러를 만듭니다. return 값은 "사용자 삭제"입니다.
    @DeleteMapping("")
    public ResponseEntity<?> DeleteUser(@AuthenticationPrincipal UserDetails userDetails){
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}