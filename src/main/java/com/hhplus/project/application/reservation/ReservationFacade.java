package com.hhplus.project.application.reservation;

import com.hhplus.project.application.reservation.dto.CreateReservationCriteria;
import com.hhplus.project.application.reservation.dto.FindReservationListResult;
import com.hhplus.project.application.reservation.dto.UpdateReservationCriteria;
import com.hhplus.project.domain.auth.TokenService;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventService;
import com.hhplus.project.domain.reservation.ReservationService;
import com.hhplus.project.domain.reservation.dto.FindReservationListInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ReservationFacade {

    private final EventService eventService;
    private final ReservationService reservationService;
    private final TokenService tokenService;

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

    /**
     * 예약 리스트 조회
     */
    public Page<FindReservationListResult.Result> findReservationList(String authorization, Pageable pageable) {
        String accessToken = tokenService.extractTokenFromHeader(authorization);
        Long memberId = tokenService.getMemberIdOfAccessToken(accessToken);
        Page<FindReservationListInfo.Info> info = reservationService.findReservationList(memberId, pageable);
        return info.map(item -> FindReservationListResult.Result.from(item, LocalDateTime.now()));
    }
}
