package com.hhplus.project.infra.reservation;

import com.hhplus.project.infra.reservation.entity.ReservationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationHistoryJpaRepository extends JpaRepository<ReservationHistoryEntity, Long> {
    List<ReservationHistoryEntity> findAllByReservationId(Long reservationId);
}
