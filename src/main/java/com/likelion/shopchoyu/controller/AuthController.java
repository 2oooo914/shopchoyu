package com.likelion.shopchoyu.controller;

import com.likelion.shopchoyu.dto.request.LoginRequestDto;
import com.likelion.shopchoyu.dto.response.JwtDto;
import com.likelion.shopchoyu.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Tag(name = "인증 API", description = "인증 관련 컨트롤러 입니다.")
public class AuthController {

    private final AuthService authService;

    //토큰 재발급 API

    @Operation(method = "POST", summary = "토큰 재발급", description = "토큰을 재발급합니다. Access Token과 Refresh Token을 Body에 담아서 전송합니다.")
    @PostMapping("/auth/reissue")
    public ResponseEntity<?> reissue(@RequestBody JwtDto jwtDto) {

        log.info("[ Auth Controller ] 토큰을 재발급합니다. ");

        return ResponseEntity.ok(authService.reissueToken(jwtDto));
    }

    @Operation(method = "POST", summary = "로그인", description = "email과 password를 Body에 담아 전송합니다. access token과 refresh token이 발급됩니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return null;
    }
}