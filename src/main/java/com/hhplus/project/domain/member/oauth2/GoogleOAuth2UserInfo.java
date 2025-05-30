package com.hhplus.project.domain.member.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }

    @Override
    public String getNickname() {
        // (임시) 이름을 닉네임으로 설정
        return (String)attributes.get("given_name");
    }

    @Override
    public String getProfileImgPath() {
        return (String)attributes.get("picture");
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.GOOGLE;
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("sub");
    }

}
