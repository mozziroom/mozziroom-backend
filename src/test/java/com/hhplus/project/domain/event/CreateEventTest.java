package com.hhplus.project.domain.event;

import com.hhplus.project.support.BaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class CreateEventTest {

    @Test
    @DisplayName(
            "이벤트 생성 Command 를 create 메소드를 사용하여 생성 시,  " +
            "이벤트 제목 유효성을 만족하지 못하면" +
            "EventException ( BaseException ) 이 발생한다. ")
    void baseException_NICK_REGEX_EXCEPTION(){
        // given
        Long categoryId = 1L;
        Long locationId = 1L;
        String name     = "C"; // TITLE_REGEX Exception
        String content  = "123456789012345678901234567890";
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt   = LocalDateTime.now().plusHours(2);
        Long hostId           = 1L;
        Integer capacity      = 30;
        String approveType    = "A";
        Boolean isOnline      = Boolean.FALSE;
        String locationDetail = "Location Detail Test";
        RecurringRules rule   = null;

        // when
        Exception exception = Assertions.assertThrows(BaseException.class,()->
            CreateEvent.Command.create(
                    locationId,
                    categoryId,
                    name,
                    content,
                    startAt,
                    endAt,
                    hostId,
                    capacity,
                    approveType,
                    isOnline,
                    locationDetail,
                    rule
            )
        );

        // then
        Assertions.assertEquals("제목의 조건에 만족하지 않습니다. 조건 : 한글,영문 대소문자, 숫자, 이모지, 특수기호(@,#,$,%,^,(,),[,],!,?)",exception.getMessage());
    }

    @Test
    @DisplayName(
            "이벤트 생성 Command 를 create 메소드를 사용하여 생성 시,  " +
                    "이벤트 이벤트 시작 종료일 유효성을 만족하지 못하면" +
                    "EventException ( BaseException ) 이 발생한다. ")
    void baseException_DATE_SETTING(){
        // given
        Long categoryId = 1L;
        Long locationId = 1L;
        String name     = "아랫마을깡소주"; // TITLE_REGEX Exception
        String content  = "123456789012345678901234567890";
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt   = LocalDateTime.now();
        Long hostId           = 1L;
        Integer capacity      = 30;
        String approveType    = "A";
        Boolean isOnline      = Boolean.FALSE;
        String locationDetail = "Location Detail Test";
        RecurringRules rule   = null;

        // when
        Exception exception = Assertions.assertThrows(BaseException.class,()->
                CreateEvent.Command.create(
                        locationId,
                        categoryId,
                        name,
                        content,
                        startAt,
                        endAt,
                        hostId,
                        capacity,
                        approveType,
                        isOnline,
                        locationDetail,
                        rule
                )
        );

        // then
        Assertions.assertEquals("이벤트의 시간 설정이 잘못 되었습니다.",exception.getMessage());
    }

}