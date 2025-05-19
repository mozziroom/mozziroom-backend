package com.hhplus.project.interfaces.reservation.dto;

import com.hhplus.project.application.reservation.dto.CreateReservationCriteria;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateReservationRequest() {

    public record Request(
            @Schema(description = "이벤트 Id", example = "1")
            Long eventId,

            @Schema(description = "멤버 Id", example = "1")
            Long memberId
    ){
        public CreateReservationCriteria.Criteria toCriteria() {
            return new CreateReservationCriteria.Criteria(
                    eventId,
                    memberId
            );
        }
    }
}
