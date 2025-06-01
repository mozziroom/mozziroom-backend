package com.hhplus.project.domain.member;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.EventException;
import com.hhplus.project.domain.event.RecurringRules;
import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.support.BaseException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public record Member(
        /** 회원 id */
        Long memberId,
        /** 회원명 */
        String name,
        /** 닉네임 */
        String nickname,
        /** 이미지 url */
        String profileImageUrl,
        /** 이메일 주소 */
        String email
) {
    public Member {

    }

    public static Member create(
            Long memberId,
            String name,
            String nickname,
            String profileImageUrl,
            String email
    ) {
        return new Member(memberId,
                name,
                nickname,
                profileImageUrl,
                email
        );
    }
}
