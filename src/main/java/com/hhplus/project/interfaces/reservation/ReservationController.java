package com.hhplus.project.interfaces.reservation;

import com.hhplus.project.support.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reservation API", description = "이벤트 예약을 관리합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    @Operation(summary = "이벤트 예약 API", description = "이벤트를 예약하고, 상태를 반환합니다.")
    @PostMapping("/{eventId}")
    public ApiResponse<Void> doReservation(
            @Parameter(description = "이벤트ID") @PathVariable Long eventId
    ) {
        return ApiResponse.ok();
    }

    @Operation(summary = "이벤트 예약취소 API", description = "이벤트 예약을 취소하고, 상태를 반환합니다.")
    @PatchMapping("/{eventId}")
    public ApiResponse<Void> cancelReservation(
            @Parameter(description = "이벤트ID") @PathVariable Long eventId
    ) {
        return ApiResponse.ok();
    }
}