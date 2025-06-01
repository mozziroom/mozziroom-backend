package com.hhplus.project.interfaces.reservation;

import com.hhplus.project.application.reservation.ReservationFacade;
import com.hhplus.project.interfaces.reservation.dto.CreateReservationRequest;
import com.hhplus.project.interfaces.reservation.dto.UpdateReservationRequest;
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
    private final ReservationFacade reservationFacade;

    @Operation(summary = "이벤트 예약 API", description = "이벤트를 예약하고, 상태를 반환합니다.")
    @PostMapping("/{eventId}")
    public ApiResponse<Void> doReservation(
            @Parameter(description = "이벤트ID") @PathVariable Long eventId,
            @Parameter(description = "회원 ID") @RequestHeader(name = "Member-Id") Long memberId
    ) {
        reservationFacade.reserveEvent((new CreateReservationRequest.Request(eventId, memberId)).toCriteria());
        return ApiResponse.ok();
    }

    @Operation(summary = "이벤트 예약취소 API", description = "이벤트 예약을 취소하고, 상태를 반환합니다.")
    @PatchMapping("/{reservationId}")
    public ApiResponse<Void> cancelReservation(
            @Parameter(description = "예약 ID") @PathVariable Long reservationId,
            @Parameter(description = "회원 ID") @RequestHeader(name = "Member-Id") Long memberId
    ) {
        reservationFacade.cancelReservation((new UpdateReservationRequest.Request(reservationId, memberId)).toCriteria());
        return ApiResponse.ok();
    }
}