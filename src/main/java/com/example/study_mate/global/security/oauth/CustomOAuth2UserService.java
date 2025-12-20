package com.example.study_mate.global.security.oauth;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.enums.Gender;
import com.example.study_mate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider =
                userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo;

        //provider 구분 ( 카카오 추가여기서)
        if ("naver".equals(provider)) {
            userInfo = new NaverUserDetails(oAuth2User.getAttributes());
        }else {
            throw new OAuth2AuthenticationException(
                    "Unsupported provider: " + provider
            );
        }

        Member member = memberRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> register(userInfo));

        return new CustomOAuth2User(member);
    }

    private Member register(OAuth2UserInfo userInfo) {

        String username = userInfo.getProvider().toLowerCase()
                + "_" + userInfo.getProviderId();

        Gender gender = null;
        if (userInfo instanceof NaverUserDetails naver) {
            gender = "M".equals(naver.getGender())
                    ? Gender.MALE
                    : Gender.FEMALE;
        }
        return memberRepository.save(
                Member.builder()
                        .username(username)
                        .password(null)          // 소셜 로그인
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .gender(gender)
                        .provider(userInfo.getProvider())
                        .providerId(userInfo.getProviderId())
                        .build()
        );
    }
}
