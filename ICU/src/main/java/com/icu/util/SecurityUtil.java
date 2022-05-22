package com.icu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {
    }

    // JwtFilter 클래스의 doFilter 메소드에서 저장한 Security Context의 인증 정보에서 username을 리턴
    public static Optional<String> getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String userId = null;
        // 왼쪽 참조 변수가 오른쪽 타입으로 변환 가능시 true
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            userId = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            userId = (String) authentication.getPrincipal();
        }
        // 일반 객체뿐만 아니라 null값까지 입력으로 받을 수 있다
        return Optional.ofNullable(userId);
    }
}

