package com.hhplus.project.application.reservation.dto;

import com.hhplus.project.domain.reservation.ReservationEnums;
import com.hhplus.project.domain.reservation.dto.FindReservationListInfo;

import java.time.LocalDateTime;

public record FindReservationListResult() {
    public record Result(
            Long reservationId,
            Long eventId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            ReservationEnums.Status status,
            String hostName,
            boolean cancellable
    ) {
        public static Result from(FindReservationListInfo.Info info, LocalDateTime dateTime) {
            return new FindReservationListResult.Result(
                    info.reservationId(),
                    info.eventId(),
                    info.name(),
                    info.content(),
                    info.startAt(),
                    info.endAt(),
                    info.status(),
                    info.hostName(),
                    info.isCancellable(dateTime)
            );
        }
    }
}
