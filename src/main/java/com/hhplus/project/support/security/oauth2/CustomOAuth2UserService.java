package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        ProviderType providerType = ProviderType.from(provider);

        OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());

        Member member = memberService.findMemberByProviderTypeAndProviderId(providerType, oAuth2UserInfo.getProviderId())
                .orElseGet(() -> memberService.save(oAuth2UserInfo.toDomain()));

        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }
}
