package com.web.SearchWeb.member.dto.Response;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;

    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null && kakaoAccount.get("email") != null) {
            return kakaoAccount.get("email").toString();
        }
        return null;
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties != null && properties.get("nickname") != null) {
            return properties.get("nickname").toString();
        }
        return null;
    }
}
