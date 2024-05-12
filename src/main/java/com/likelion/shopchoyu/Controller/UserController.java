package com.likelion.shopchoyu.Controller;

import com.likelion.shopchoyu.Dto.CreateUserRequestDto;
import com.likelion.shopchoyu.Dto.OrderResponseDto;
import com.likelion.shopchoyu.Dto.UpdateUserRequestDto;
import com.likelion.shopchoyu.Dto.UserResponseDto;
import com.likelion.shopchoyu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId){
        UserResponseDto responseDto = userService.getUser(userId);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    // 3. 사용자를 수정하는 컨트롤러를 만듭니다. return 값은 "사용자 수정"입니다.
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, UpdateUserRequestDto userUpdate){
        UserResponseDto responseDto = userService.updateUser(userId, userUpdate);
        if (responseDto != null){
            return ResponseEntity.status(HttpStatus.OK).body("사용자 수정 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 수정 실패");
        }
    }

    // 4. 사용자를 삭제하는 컨트롤러를 만듭니다. return 값은 "사용자 삭제"입니다.
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> DeleteUser(@RequestParam long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}