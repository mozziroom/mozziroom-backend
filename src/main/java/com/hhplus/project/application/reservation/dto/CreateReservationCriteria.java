package com.hhplus.project.application.reservation.dto;

import com.hhplus.project.domain.reservation.dto.CreateReservationCommand;

public record CreateReservationCriteria() {

    public record Criteria(
            Long eventId,
            Long memberId
    ) {
        public CreateReservationCommand.Command toCommand() {
            return new CreateReservationCommand.Command(
                    eventId,
                    memberId
            );
        }
    }
}
