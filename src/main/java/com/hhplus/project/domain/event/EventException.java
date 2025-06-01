package com.hhplus.project.domain.event;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventException implements ExceptionInterface {

    WRONG_CAPACITY("WRONG.CAPACITY","인원수는 1명 이상 30명 이하로 설정해야 합니다."),
    TITLE_REGEX("EVENT.TITLE.REGEX","제목의 조건에 만족하지 않습니다. 조건 : 한글,영문 대소문자, 숫자, 이모지, 특수기호(@,#,$,%,^,(,),[,],!,?)"),
    WRONG_TIME_SETTING("EVENT.WRONG.TIME.SET","이벤트의 시간 설정이 잘못 되었습니다."),

    CATEGORY_NOT_FOUND("CATEGORY.NOT.FOUND","카테고리를 찾을 수 없습니다."),
    LOCATION_NOT_FOUND("LOCATION.NOT.FOUND","장소를 찾을 수 없습니다."),


    SAMPLE("EVENT.EXCEPTION.SAMPLE", "에러 샘플"),
    ALREADY_ENDED_EVENT("ALREADY.ENDED.EVENT", "이미 종료된 이벤트입니다."),
    NOT_EXISTS_EVENT("NOT.EXISTS.EVENT", "이벤트가 존재하지 않습니다."),
    EVENT_NOT_FOUND("EVENT.NOT.FOUND", "이벤트를 찾을 수 없습니다")
    ;

    private final String code;
    private final String message;
}
