package com.likelion.shopchoyu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {
    //JWT(Json Web Token)를 위한 JwtDto 클래스, 인증 과정에서 생성된 access token과 refresh token을 저장하기 위해 사용

    public String accessToken;

    public String refreshToken;

    //클라이언트에게 access token과 refresh token을 전달
    //refresh token을 통해 access token이 만료되었을 때 새로운 access token을 발급받을 수 있는 기능을 제공
}