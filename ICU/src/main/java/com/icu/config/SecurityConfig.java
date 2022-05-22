package com.icu.config;

import com.icu.util.token.JwtAccessDeniedHandler;
import com.icu.util.token.JwtAuthenticationEntryPoint;
import com.icu.util.token.JwtSecurityConfig;
import com.icu.util.token.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {

        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling() // 예외처리 기능
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 실패시 처리
                .accessDeniedHandler(jwtAccessDeniedHandler) // 인가 실패시 처리
                .and()
                .sessionManagement() // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // Token이 없어도 호출할 수 있도록 허용
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/user/signin").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/stutest/update-mytest").permitAll()
                .antMatchers("/userimg/**").permitAll()
                .antMatchers("/tproblemvideo/video.mp4").permitAll()
                .anyRequest().authenticated() // 나머지는 권한 검증
                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // 사용자 정의 설정
    }

}
