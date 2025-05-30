package com.hhplus.project.domain.member.oauth2;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        // (임시) 닉네임
        return (String) ((Map)((Map) attributes.get("kakao_account")).get("profile")).get("nickname");
    }

    @Override
    public String getNickname() {
        return (String) ((Map)((Map) attributes.get("kakao_account")).get("profile")).get("nickname");
    }

    @Override
    public String getProfileImgPath() {
        return (String) ((Map)((Map) attributes.get("kakao_account")).get("profile")).get("profile_image_url");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.KAKAO;
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("id");
    }
}
