package com.example.study_mate.global.security.oauth;

import com.example.study_mate.global.utill.JwtTokenProvider;
import com.example.study_mate.member.dto.res.JwtTokenPair;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        CustomOAuth2User oAuth2User =
                (CustomOAuth2User) authentication.getPrincipal();
        Long memberId = oAuth2User.getMember().getId();

        // Token 생성
        JwtTokenPair tokenPair =
                jwtTokenProvider.createToken(memberId);

        log.info("OAuth2 로그인 성공, memberId={}", memberId);

        //Swagger 테스트용 redirect
        String redirectUrl =
                "http://localhost:8080/swagger-ui/index.html"
                        + "?accessToken=" + URLEncoder.encode(
                        tokenPair.accessToken(),
                        StandardCharsets.UTF_8
                );

        response.sendRedirect(redirectUrl);
    }
}
