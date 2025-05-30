package com.hhplus.project.domain.member.oauth2;

import com.hhplus.project.domain.member.Member;
import com.hhplus.project.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        // provider와 providerId(sub)으로 존재하는 회원인지 조회, 미존재면 등록
        Member.Info member = memberRepository.findByProviderAndProviderId(provider, oAuth2UserInfo.getProviderId())
                .orElseGet(() -> memberRepository.save(oAuth2UserInfo.toEntity()))
                .toDomain();

        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }
}
