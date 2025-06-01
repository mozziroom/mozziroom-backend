package com.hhplus.project.support.security.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getNickname() {
        // (임시) 이름을 닉네임으로 설정
        return attributes.get("given_name").toString();
    }

    @Override
    public String getProfileImgPath() {
        return attributes.get("picture").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.GOOGLE;
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

}
