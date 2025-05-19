package com.hhplus.project.domain.reservation;

import com.hhplus.project.support.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationException implements ExceptionInterface {
    NOT_MATCHED_RESERVATION("RESERVATION.NOT.MATCHED.RESERVATION", "해당 예약이 존재하지 않습니다."),
    NOT_MATCHED_RESERVATION_HISTORY("RESERVATION.NOT.MATCHED.RESERVATION.HISTORY", "해당 예약 내역(history)이 존재하지 않습니다."),
    SAMPLE("RESERVATION.EXCEPTION.SAMPLE", "에러 샘플"),
    ;

    private final String code;
    private final String message;
}