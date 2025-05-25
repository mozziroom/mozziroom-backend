package com.hhplus.project.infra.reservation;

import com.hhplus.project.domain.reservation.Reservation;
import com.hhplus.project.domain.reservation.ReservationException;
import com.hhplus.project.domain.reservation.ReservationRepository;
import com.hhplus.project.infra.reservation.entity.ReservationEntity;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationImplRepository implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation findById(Long reservationId) {
        return reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(ReservationException.NOT_MATCHED_RESERVATION)).toDomain();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(ReservationEntity.fromDomain(reservation)).toDomain();
    }
}
