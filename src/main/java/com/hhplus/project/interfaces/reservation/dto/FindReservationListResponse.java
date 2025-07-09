package com.hhplus.project.interfaces.reservation.dto;

import com.hhplus.project.application.reservation.dto.FindReservationListResult;
import com.hhplus.project.domain.reservation.ReservationEnums;

import java.time.LocalDateTime;

public record FindReservationListResponse() {
    public record Response(
            Long reservationId,
            Long eventId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            ReservationEnums.Status status,
            Long hostName,
            boolean cancellable
    ) {
        public static Response from(FindReservationListResult.Result result) {
            return new FindReservationListResponse.Response(
                    result.reservationId(),
                    result.eventId(),
                    result.name(),
                    result.content(),
                    result.startAt(),
                    result.endAt(),
                    result.status(),
                    result.hostName(),
                    result.cancellable()
            );
        }
    }
}
