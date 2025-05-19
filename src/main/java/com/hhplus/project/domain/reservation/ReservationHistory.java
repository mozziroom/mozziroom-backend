package com.hhplus.project.domain.reservation;

import lombok.Getter;

@Getter
public class ReservationHistory {
    /** 예약 내역 id */
    private Long reservationHistoryId;

    /** 예약 id */
    private Long reservationId;

    /** 예약 상태 (승인대기, 승인, 취소, 거절) */
    private ReservationEnums.Status status;

    public static ReservationHistory create(
            Long reservationHistoryId,
            Long reservationId,
            ReservationEnums.Status status
    ) {
        ReservationHistory reservationHistory = new ReservationHistory();
        reservationHistory.reservationHistoryId = reservationHistoryId;
        reservationHistory.reservationId = reservationId;
        reservationHistory.status = status;

        return reservationHistory;
    }
}
