package com.hhplus.project.domain.reservation.dto;

import com.hhplus.project.domain.reservation.Reservation;

public record CreateReservationCommand() {

    public record Command(
            Long eventId,
            Long memberId
    ){

        public Reservation toDomain() {
            return Reservation.create(
                    null,
                    eventId,
                    memberId,
                    null
            );
        }
    }
}
