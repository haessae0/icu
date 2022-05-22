package com.icu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // CORS요청이 어떻게 허용 origin, 헤더, 메소드등 처리되어야하는지 방법을 지정
        CorsConfiguration config = new CorsConfiguration();
        // 사용자 자격 증명이 지원되는지 여부
        config.setAllowCredentials(true);
        // 허용할 URL, 헤더, 메소드
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
