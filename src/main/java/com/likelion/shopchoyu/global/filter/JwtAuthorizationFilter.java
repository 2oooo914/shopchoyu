package com.likelion.shopchoyu.global.filter;

import com.likelion.shopchoyu.userDetails.CustomUserDetails;
import com.likelion.shopchoyu.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
//    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("[ JwtAuthorizationFilter ] 인가 필터 작동");

        try {
            //Request 에서 access token 추출
            //요청에서 JWT 액세스 토큰을 추출, 토큰이 존재하지 않거나, 로그아웃 처리된 토큰인 경우 필터 처리를 건너뛰고 다음 필터로 넘어감
            String accessToken = jwtUtil.resolveAccessToken(request);

            // accessToken 없이 접근할 경우 필터를 건너뜀
            if (accessToken == null) {
                log.info("[ JwtAuthorizationFilter ] Access Token 이 존재하지 않음. 필터를 건너뜁니다.");
                filterChain.doFilter(request, response);
                return;
            }

            // logout 처리된 accessToken -> 원래는 Redis 에서 해당 토큰이 logout 처리됐는지 검사함, 우리는 Redis 를 다루지 않을 것임.
//            if (redisUtil.get(accessToken) != null && redisUtil.get(accessToken).equals("logout")) {
//                logger.info("[*] Logout accessToken");
//                filterChain.doFilter(request, response);
//                return;
//            }

            authenticateAccessToken(accessToken);
            log.info("[ JwtAuthorizationFilter ] 종료. 다음 필터로 넘어갑니다.");
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            logger.warn("[ JwtAuthorizationFilter ] accessToken 이 만료되었습니다.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 return
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Access Token 이 만료되었습니다.");
        }
    }

    private void authenticateAccessToken(String accessToken) {
        log.info("[ JwtAuthorizationFilter ] 토큰으로 인가 과정을 시작합니다. ");

        //AccessToken 유효성 검증
        jwtUtil.validateToken(accessToken);
        log.info("[ JwtAuthorizationFilter ] Access Token 유효성 검증 성공. ");

        //CustomUserDetail 객체 생성, Spring Security에서 사용자의 인증 정보
        CustomUserDetails userDetails = new CustomUserDetails(
                //토큰에서 사용자 정보(이메일, 역할 등)를 추출하여 CustomUserDetails 객체 생성
                jwtUtil.getEmail(accessToken),
                null,
                jwtUtil.getRoles(accessToken)
        );

        log.info("[ JwtAuthorizationFilter ] UserDetails 객체 생성 성공");

        // Spring Security 인증 토큰 생성해서 SecurityContextHolder의 인증 객체로 설정
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        // SecurityContextHolder 에 현재 인증 객체 저장하여 인증 되었음을 알림
        SecurityContextHolder.getContext().setAuthentication(authToken);

        log.info("[ JwtAuthorizationFilter ] 인증 객체 저장 완료");
    }
}