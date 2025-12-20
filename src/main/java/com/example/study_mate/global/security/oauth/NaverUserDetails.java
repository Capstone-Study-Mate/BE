package com.example.study_mate.global.security.oauth;

import java.util.Map;

public class NaverUserDetails implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public NaverUserDetails(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private Map<String, Object> response() {
        return (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "NAVER";
    }

    @Override
    public String getProviderId() {
        return (String) response().get("id");
    }

    @Override
    public String getEmail() {
        return (String) response().get("email");
    }

    @Override
    public String getName() {
        return (String) response().get("name");
    }

    public String getGender() {
        return (String) response().get("gender"); // M / F
    }
}

