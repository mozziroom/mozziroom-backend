package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.infra.member.entity.MemberEntity;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    protected Object get(String... keys) {
        Object current = attributes;
        for (String key : keys) {
            if (!(current instanceof Map)) return null;
            current = ((Map<?, ?>) current).get(key);
        }
        return current;
    }

    public abstract String getName();
    public abstract String getNickname();
    public abstract String getProfileImgPath();
    public abstract String getEmail();
    public abstract ProviderType getProviderType();
    public abstract String getProviderId();


    public MemberEntity toEntity() {
        return MemberEntity.create(
                this.getName(),
                this.getNickname(),
                this.getProfileImgPath(),
                this.getEmail(),
                this.getProviderType(),
                this.getProviderId()
        );
    }
}
