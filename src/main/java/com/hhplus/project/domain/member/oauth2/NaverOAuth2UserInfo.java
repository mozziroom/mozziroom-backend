package com.hhplus.project.domain.member.oauth2;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }

    @Override
    public String getNickname() {
        return (String)attributes.get("nickname");
    }

    @Override
    public String getProfileImgPath() {
        return (String)attributes.get("profile_image");
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.NAVER;
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("id");
    }
}
