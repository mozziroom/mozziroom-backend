package com.hhplus.project.domain.event;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventException implements ExceptionInterface {

    SAMPLE("EVENT.EXCEPTION.SAMPLE", "에러 샘플"),
    ;

    private final String code;
    private final String message;
}
