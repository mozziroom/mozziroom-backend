package com.hhplus.project.application.reservation;

import com.hhplus.project.application.reservation.dto.CreateReservationCriteria;
import com.hhplus.project.application.reservation.dto.UpdateReservationCriteria;
//import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ReservationFacade {

//    private final EventService eventService;
    private final ReservationService reservationService;

    /**
     * 이벤트 예약
     * TODO - 자동수락/수동수락
     */
    @Transactional
    public void reserveEvent(CreateReservationCriteria.Criteria criteria) {
        // 이벤트 존재 여부 확인
//        Event event = eventService.getEvent(criteria.eventId());

        // 예약 생성
        reservationService.reserve(criteria.toCommand());
    }

    /**
     * 이벤트 예약 취소
     */
    @Transactional
    public void cancelReservation(UpdateReservationCriteria.Criteria criteria) {
        // 예약 취소
        reservationService.cancel(criteria.toCommand());
    }
}