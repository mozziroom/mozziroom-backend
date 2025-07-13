package com.hhplus.project.domain.reservation.dto;

import com.hhplus.project.domain.reservation.ReservationEnums;

import java.time.LocalDateTime;

public record FindReservationListInfo() {

    public record Info(
            Long reservationId,
            Long eventId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            ReservationEnums.Status status,
            String hostName
    ) {
        // 파라미터로 받은 일자시간과 비교해서 이벤트 시작 시간 전이면 예약 취소 가능
        public boolean isCancellable(LocalDateTime dateTime) {
            return dateTime.isBefore(startAt);
        }
    }
}
