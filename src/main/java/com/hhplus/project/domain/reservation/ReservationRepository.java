package com.hhplus.project.domain.reservation;

public interface ReservationRepository {
    Reservation findById(Long reservationId);
    Reservation save(Reservation reservation);
    Reservation findByEventIdAndMemberId(Long eventId, Long memberId);
}
