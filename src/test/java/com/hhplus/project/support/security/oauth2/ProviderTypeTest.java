package com.hhplus.project.support.security.oauth2;

import com.hhplus.project.domain.member.MemberException;
import com.hhplus.project.support.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProviderTypeTest {

    @Test
    @DisplayName("ProviderType Enum에 정의된 플랫폼이 아니면 예외를 발생한다.")
    void providerTypeTest_fail() {
        // given
        String provider = "notSupportedProvider";

        // when // then
        assertThatThrownBy(() -> ProviderType.from(provider))
                .isInstanceOf(BaseException.class)
                .hasMessage(MemberException.PROVIDER_TYPE_NOT_FOUND.getMessage());

    }

    @ParameterizedTest
    @EnumSource(ProviderType.class)
    @DisplayName("ProviderType Enum에 정의된 provider 값으로 from() 호출 시 Enum이 정상적으로 반환된다.")
    void providerTypesTest_fail(ProviderType providerType) {
        // given
        String provider = providerType.getProvider();

        // when
        ProviderType convertedProviderType = ProviderType.from(provider);

        // then
        assertThat(convertedProviderType).isEqualTo(providerType);
    }

}