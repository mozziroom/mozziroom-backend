package com.hhplus.project.infra.reservation;

import com.hhplus.project.domain.reservation.Reservation;
import com.hhplus.project.infra.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
    Reservation findByEventIdAndMemberId(Long eventId, Long memberId);
}
