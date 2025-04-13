package com.hhplus.project.interfaces.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "이벤트 예약 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    @Schema(description = "예약 상태", example = "PENDING")
    String status;
    @Schema(description = "수정 일시", example = "2025-04-10T11:37:00")
    LocalDateTime updateAt;
}
