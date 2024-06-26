package com.likelion.shopchoyu.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.shopchoyu.dto.response.JwtDto;
import com.likelion.shopchoyu.dto.request.LoginRequestDto;
import com.likelion.shopchoyu.userDetails.CustomUserDetails;
import com.likelion.shopchoyu.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    //Spring Security와 JWT를 사용하여 인증 시스템을 구축하는 데 있어 기본적인 템플릿 역할
    //사용자가 로그인 시도할 때 이메일과 비밀번호를 통한 인증 과정을 처리
    //성공적으로 인증이 되면 JWT 액세스 토큰과 리프레시 토큰을 생성하여 클라이언트에 반환

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //로그인 시도 메서드
    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response) throws AuthenticationException {

        log.info("[ Login Filter ]  로그인 시도 : Custom Login Filter 작동 ");
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto requestBody;
        try {
            requestBody = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
            //HttpServletRequest에서 로그인 요청 데이터를 읽어 LoginRequestDto로 변환
        } catch (IOException e) {
            throw new AuthenticationServiceException("[ Login Filter ] Request Body 파싱 과정에서 오류가 발생했습니다.");
        }

        //Request Body 에서 추출
        String email = requestBody.getEmail(); //Email 추출
        String password = requestBody.getPassword(); //password 추출
        log.info("[ Login Filter ]  Email ---> {} ", email);
        log.info("[ Login Filter ]  Password ---> {} ", password);

        //UserNamePasswordToken 생성 (인증용 객체)
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(email, password, null);

        log.info("[ Login Filter ] 인증용 객체 UsernamePasswordAuthenticationToken 이 생성되었습니다. ");
        log.info("[ Login Filter ] 인증을 시도합니다.");

        //authenticationManager에 전달해서 인증 시도
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시
    @Override
    protected void successfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain,
            @NonNull Authentication authentication) throws IOException {


        log.info("[ Login Filter ] 로그인에 성공 하였습니다.");

        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

        //인증이 성공하면, 해당 사용자의 CustomUserDetails 정보를 기반으로 JWT 액세스 토큰과 리프레시 토큰을 생성
        //Client 에게 줄 Response 를 Build
        JwtDto jwtDto = JwtDto.builder()
                .accessToken(jwtUtil.createJwtAccessToken(customUserDetails)) //access token 생성
                .refreshToken(jwtUtil.createJwtRefreshToken(customUserDetails)) //refresh token 생성
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value()); //Response 의 Status 를 200으로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        //JwtDto 객체에 담겨 클라이언트에 JSON 형태로 반환

        //Body 에 토큰이 담긴 Response 쓰기
        response.getWriter().write(objectMapper.writeValueAsString(jwtDto));
    }

    //로그인 실패시
    @Override
    protected void unsuccessfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException failed) throws IOException {
        //  인증 과정에서 예외가 발생하면, 예외 유형에 따라 적절한 에러 메시지를 클라이언트에 반환

        log.info("[ Login Filter ] 로그인에 실패하였습니다.");

        String errorMessage;
        if (failed instanceof BadCredentialsException) {
            errorMessage = "잘못된 정보입니다.";
        } else if (failed instanceof LockedException) {
            errorMessage = "계정이 잠금 상태입니다.";
        } else if (failed instanceof DisabledException) {
            errorMessage = "계정이 비활성화 되었습니다.";
        } else if (failed instanceof UsernameNotFoundException) {
            errorMessage = "계정을 찾을 수 없습니다.";
        } else if (failed instanceof AuthenticationServiceException) {
            errorMessage = "Request Body 파싱 중 오류가 발생했습니다.";
        } else {
            errorMessage = "인증에 실패했습니다.";
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); //Status 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorMessage)); //error message 와 함께 Response 작성
    }

}