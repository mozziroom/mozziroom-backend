package com.hhplus.project.domain.reservation;

import java.util.List;

public interface ReservationHistoryRepository {
    ReservationHistory findById(Long reservationHistoryId);
    List<ReservationHistory> findAllByReservationId(Long reservationId);
    ReservationHistory save(Reservation reservation);
}
