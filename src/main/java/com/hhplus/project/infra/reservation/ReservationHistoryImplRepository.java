package com.hhplus.project.infra.reservation;

import com.hhplus.project.domain.reservation.*;
import com.hhplus.project.infra.reservation.entity.ReservationHistoryEntity;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationHistoryImplRepository implements ReservationHistoryRepository {

    private final ReservationHistoryJpaRepository reservationHistoryJpaRepository;

    @Override
    public ReservationHistory findById(Long reservationHistoryId) {
        return reservationHistoryJpaRepository.findById(reservationHistoryId)
                .orElseThrow(() -> new BaseException(ReservationException.NOT_MATCHED_RESERVATION_HISTORY)).toDomain();
    }

    @Override
    public List<ReservationHistory> findAllByReservationId(Long reservationId) {
        return reservationHistoryJpaRepository.findAllByReservationId(reservationId)
                .stream()
                .map(ReservationHistoryEntity::toDomain)
                .toList();
    }

    @Override
    public ReservationHistory save(Reservation reservation) {
        return reservationHistoryJpaRepository.save(ReservationHistoryEntity.fromDomain(reservation)).toDomain();
    }
}
