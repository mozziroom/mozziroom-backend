package com.hhplus.project.application.reservation.dto;

import com.hhplus.project.domain.reservation.dto.UpdateReservationCommand;

public record UpdateReservationCriteria() {

    public record Criteria(
            Long reservationId,
            Long memberId
    ) {
        public UpdateReservationCommand.Command toCommand() {
            return new UpdateReservationCommand.Command(
                    reservationId,
                    memberId
            );
        }
    }
}
