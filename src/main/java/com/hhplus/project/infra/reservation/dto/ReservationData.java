package com.hhplus.project.infra.reservation.dto;

import com.hhplus.project.domain.reservation.ReservationEnums;
import com.hhplus.project.domain.reservation.dto.FindReservationListInfo;

import java.time.LocalDateTime;

public record ReservationData(
        Long reservationId,
        Long eventId,
        String name,
        String content,
        LocalDateTime startAt,
        LocalDateTime endAt,
        ReservationEnums.Status status,
        Long hostName
) {
    public FindReservationListInfo.Info toInfo() {
        return new FindReservationListInfo.Info(
                reservationId,
                eventId,
                name,
                content,
                startAt,
                endAt,
                status,
                hostName
        );
    }
}
