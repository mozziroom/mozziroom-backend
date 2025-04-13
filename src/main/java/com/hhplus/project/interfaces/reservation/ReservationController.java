package com.hhplus.project.interfaces.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Reservation API", description = "이벤트 예약을 관리합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    @Operation(summary = "이벤트 예약 API", description = "이벤트를 예약하고, 상태를 반환합니다.")
    @PostMapping("{eventId}")
    public ReservationResponse doReservation(
            @Parameter(description = "이벤트ID") @PathVariable long eventId
    ) {
        String status = "PENDING";
        LocalDateTime updateAt = LocalDateTime.now();

        return new ReservationResponse(status, updateAt);
    }

    @Operation(summary = "이벤트 예약취소 API", description = "이벤트 예약을 취소하고, 상태를 반환합니다.")
    @PatchMapping("{eventId}")
    public ReservationResponse cancelReservation(
            @Parameter(description = "이벤트ID") @PathVariable long eventId
    ) {
        String status = "CANCELED";
        LocalDateTime updateAt = LocalDateTime.now();

        return new ReservationResponse(status, updateAt);
    }
}
