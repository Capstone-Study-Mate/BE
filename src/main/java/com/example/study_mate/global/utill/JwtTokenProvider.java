package com.example.study_mate.global.utill;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.global.exception.code.GeneralErrorCode;
import com.example.study_mate.member.dto.res.JwtTokenPair;
import com.example.study_mate.member.security.MemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidityMilliSeconds;   // 15분

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidityMilliSeconds;  // 14일

    private SecretKey key;

    private final MemberDetailsService memberDetailsService;

    @PostConstruct
    public void initKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenPair createToken(Long memberId) {
        return new JwtTokenPair(
                generateToken(memberId,accessTokenValidityMilliSeconds),
                generateToken(memberId,refreshTokenValidityMilliSeconds)
        );
    }

    public String generateToken(Long memberId, long expirationTime) {
        long now = System.currentTimeMillis();
        Date expirationDate = new Date(now + expirationTime);

        return Jwts.builder()
                .subject(memberId.toString())   // 기존 예약어를 덮어쓸 위험이 있어
                .issuedAt(new Date())           // set 사용을 지양
                .expiration(expirationDate)
                .signWith(key)
                .compact();

    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;

        } catch (ExpiredJwtException e) {
            throw new BusinessException(GeneralErrorCode.AUTH_EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(GeneralErrorCode.AUTH_INVALID_TOKEN);
        }
    }


    public String resolveBearerToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }


    public Authentication getAuthentication(String token) {

        // 1. 토큰 파싱 + 서명 검증
        Claims claims = Jwts.parser()
                .verifyWith(key)              // SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // 2. subject 에서 memberId 추출
        String memberId = claims.getSubject();
        if (memberId == null) {
            throw new BusinessException(GeneralErrorCode.AUTH_INVALID_TOKEN);
        }

        // 3. UserDetails 생성
        UserDetails userDetails =
                memberDetailsService.loadUserByUsername(memberId);

        // 4. Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }



    public Long getMemberId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}