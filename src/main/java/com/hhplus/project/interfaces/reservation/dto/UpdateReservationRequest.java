package com.hhplus.project.interfaces.reservation.dto;

import com.hhplus.project.application.reservation.dto.UpdateReservationCriteria;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateReservationRequest() {

    public record Request(
            @Schema(description = "이벤트 예약 Id", example = "1")
            Long reservationId,

            @Schema(description = "멤버 Id", example = "1")
            Long memberId
    ){
        public UpdateReservationCriteria.Criteria toCriteria() {
            return new UpdateReservationCriteria.Criteria(
                    reservationId,
                    memberId
            );
        }
    }
}
