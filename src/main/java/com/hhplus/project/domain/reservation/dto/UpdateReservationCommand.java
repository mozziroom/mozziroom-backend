package com.hhplus.project.domain.reservation.dto;

public record UpdateReservationCommand() {

    public record Command(
            Long reservationId,
            Long memberId
    ){

    }
}