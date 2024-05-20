package com.likelion.shopchoyu.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

// access = AccessLevel.PRIVATE을 통해 이 생성자가 private하게 설정되어, 외부에서 이 클래스의 인스턴스를 생성하는 것을 방지
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CorsConfig implements WebMvcConfigurer {

    public static CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //데이터 교환이 가능한 URL 지정
        ArrayList<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("http://localhost:8000");
        allowedOriginPatterns.add("http://localhost:3000");

        //허용하는 HTTP METHOD 지정
        ArrayList<String> allowedHttpMethods = new ArrayList<>();
        allowedHttpMethods.add("GET");
        allowedHttpMethods.add("POST");
        allowedHttpMethods.add("PUT");
        allowedHttpMethods.add("DELETE");

        // 설정한 허용 URL과 메서드를 CORS 설정에 적용
        configuration.setAllowedOrigins(allowedOriginPatterns);
        configuration.setAllowedMethods(allowedHttpMethods);

        // URL 기반 CORS 설정 소스를 생성하고, 모든 경로("/**")에 대해 위에서 생성한 CORS 설정을 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}