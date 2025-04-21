package com.hhplus.project.interfaces.reservation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class ReservationDetail {

    public record Request(

    ){
    }

    @Schema(description = "이벤트 예약 Response")
    public record Response(
        @Schema(description = "예약 상태", example = "PENDING")
        String status,
        @Schema(description = "수정 일시", example = "2025-04-10T11:37:00")
        LocalDateTime updateAt
    ){
        public static ReservationDetail.Response create(){

            return new ReservationDetail.Response(
                    "PENDING",
                    LocalDateTime.now()
            );
        }
    }
}