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

    @Operation(summary = "이벤트 예약 신청 API", description = "이벤트 예약을 신청합니다.")
    @PostMapping("/{eventId}")
    public ApiResponse<Void> doReservation(
            @Parameter(description = "이벤트ID") @PathVariable Long eventId,
            @Parameter(description = "회원 ID") @RequestHeader(name = "Member-Id") Long memberId
    ) {
        reservationFacade.reserveEvent((new CreateReservationRequest.Request(eventId, memberId)).toCriteria());
        return ApiResponse.ok();
    }

    @Operation(summary = "이벤트 예약 취소 API", description = "이벤트 예약을 취소합니다.")
    @PatchMapping("/{reservationId}/cancel")
    public ApiResponse<Void> cancelReservation(
            @Parameter(description = "예약 ID") @PathVariable Long reservationId,
            @Parameter(description = "회원 ID") @RequestHeader(name = "Member-Id") Long memberId
    ) {
        reservationFacade.cancelReservation((new UpdateReservationRequest.Request(reservationId, memberId)).toCriteria());
        return ApiResponse.ok();
    }

    @Operation(summary = "이벤트 예약 승인 API", description = "주최자가 예약을 승인합니다.")
    @PatchMapping("/{reservationId}/approve")
    public ApiResponse<Void> approveReservation(
            @Parameter(description = "예약 ID") @PathVariable Long reservationId,
            @Parameter(description = "주최자 ID") @RequestHeader(name = "Member-Id") Long hostId
    ) {
        reservationFacade.approveReservation((new UpdateReservationRequest.Request(reservationId, hostId)).toCriteria());
        return ApiResponse.ok();
    }

    @Operation(summary = "이벤트 예약 거절 API", description = "주최자가 예약을 거절합니다.")
    @PatchMapping("/{reservationId}/reject")
    public ApiResponse<Void> rejectReservation(
            @Parameter(description = "예약 ID") @PathVariable Long reservationId,
            @Parameter(description = "주최자 ID") @RequestHeader(name = "Member-Id") Long hostId
    ) {
        reservationFacade.rejectReservation((new UpdateReservationRequest.Request(reservationId, hostId)).toCriteria());
        return ApiResponse.ok();
    }
}