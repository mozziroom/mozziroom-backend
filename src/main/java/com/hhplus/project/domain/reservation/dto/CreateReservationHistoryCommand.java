package com.hhplus.project.domain.reservation.dto;

import com.hhplus.project.domain.reservation.ReservationEnums;
import com.hhplus.project.domain.reservation.ReservationHistory;

public record CreateReservationHistoryCommand() {

    public record Command(
            Long reservationId,
            ReservationEnums.Status status
    ) {

        public ReservationHistory toDomain() {
            return ReservationHistory.create(
                    null,
                    reservationId,
                    status
            );
        }
    }
}