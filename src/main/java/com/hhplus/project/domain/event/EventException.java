package com.hhplus.project.domain.event;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventException implements ExceptionInterface {

    EVENT_NOT_FOUND("EVENT.NOT.FOUND", "이벤트를 찾을 수 없습니다")
    ;

    private final String code;
    private final String message;
}
