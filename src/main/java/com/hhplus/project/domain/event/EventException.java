package com.hhplus.project.domain.event;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventException implements ExceptionInterface {

    SAMPLE("EVENT.EXCEPTION.SAMPLE", "에러 샘플"),
    ALREADY_ENDED_EVENT("ALREADY.ENDED.EVENT", "이미 종료된 이벤트입니다."),
    NOT_EXISTS_EVENT("NOT.EXISTS.EVENT", "이벤트가 존재하지 않습니다."),
    EVENT_NOT_FOUND("EVENT.NOT.FOUND", "이벤트를 찾을 수 없습니다")
    ;

    private final String code;
    private final String message;
}
