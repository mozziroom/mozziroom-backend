package com.hhplus.project.domain.event;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventException implements ExceptionInterface {

// CREATE EVENT EXCEPTION
    TITLE_REGEX("EVENT.TITLE.REGEX","제목의 조건에 만족하지 않습니다. 조건 : 한글,영문 대소문자, 숫자, 이모지, 특수기호(@,#,$,%,^,(,),[,],!,?)"),
    WRONG_TIME_SETTING("EVENT.WRONG.TIME.SET","이벤트의 시간 설정이 잘못 되었습니다."),
    RECURRING_TYPE("EVENT.RECURRING.TYPE.INVALID","이벤트 반복 설정 타입이 유효하지 않습니다."),
    SAMPLE("EVENT.EXCEPTION.SAMPLE", "에러 샘플"),
    ;

    private final String code;
    private final String message;
}
