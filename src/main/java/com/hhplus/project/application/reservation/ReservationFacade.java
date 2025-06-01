package com.hhplus.project.application.reservation;

import com.hhplus.project.application.reservation.dto.CreateReservationCriteria;
import com.hhplus.project.application.reservation.dto.UpdateReservationCriteria;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventService;
import com.hhplus.project.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationFacade {

    private final EventService eventService;
    private final ReservationService reservationService;

    /**
     * 이벤트 예약 신청
     */
    public void reserveEvent(CreateReservationCriteria.Criteria criteria) {
        // 이벤트 존재 여부 확인
        Event event = eventService.getEvent(criteria.eventId());

        // 예약 생성
        reservationService.reserve(criteria.toCommand(event));
    }

    /**
     * 이벤트 예약 취소
     */
    public void cancelReservation(UpdateReservationCriteria.Criteria criteria) {
        // 예약 취소
        reservationService.cancel(criteria.toCommand());
    }

    /**
     * 이벤트 예약 승인(주최자 승인)
     */
    public void approveReservation(UpdateReservationCriteria.Criteria criteria) {
        reservationService.approve(criteria.toCommand());
    }

    /**
     * 이벤트 예약 거절(주최자 거절)
     */
    public void rejectReservation(UpdateReservationCriteria.Criteria criteria) {
        reservationService.reject(criteria.toCommand());
    }
}
