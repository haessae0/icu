package com.icu.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    public static final String AUTHORITIES_KEY = "auth";

    private final String secret;

    private final long tokenValidityInMilliseconds;

    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Authentication 객체에 포함되어 있는 아이디와 권한 정보를 담은 토큰을 생성
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("com", "ICU");
        headers.put("alg", "HS512");

        return Jwts.builder().setHeader(headers).setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities).signWith(SignatureAlgorithm.HS512, key).setExpiration(validity)
                .compact();
    }

    // 토큰에 담겨있는 권한 정보들을 이용해 Authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        // username, password, role
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰을 검증하는 역할을 수행
    public boolean validateToken(String token) {
        try {
            JwsHeader header = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getHeader();
            // 헤더 값으로 1차 검증
            if (header.get("com").equals("ICU") && header.get("alg").equals("HS512")) {
                // 2차 검증
                Jwts.parser().setSigningKey(key).parseClaimsJws(token);
                return true;
            }
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}