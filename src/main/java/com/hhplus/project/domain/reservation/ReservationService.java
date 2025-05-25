package com.hhplus.project.domain.reservation;

import com.hhplus.project.domain.reservation.dto.CreateReservationCommand;
import com.hhplus.project.domain.reservation.dto.UpdateReservationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationHistoryRepository reservationHistoryRepository;

    /**
     * 이벤트 예약
     */
    public Reservation reserve(CreateReservationCommand.Command command) {
        Reservation reservation = command.toDomain();
        reservation.pending();
        reservation = reservationRepository.save(reservation);

        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 이벤트 예약 승인(주최자 승인)
     */
    public Reservation approve(UpdateReservationCommand.Command command) {
        Reservation reservation = getReservation(command.reservationId());
        reservation.approve();
        reservation = reservationRepository.save(reservation);

        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 이벤트 예약 취소(예약자 본인 취소)
     */
    public Reservation cancel(UpdateReservationCommand.Command command) {
        Reservation reservation = getReservation(command.reservationId());
        reservation.cancel();
        reservation = reservationRepository.save(reservation);

        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 이벤트 예약 거절(주최자 거절)
     */
    public Reservation reject(UpdateReservationCommand.Command command) {
        Reservation reservation = getReservation(command.reservationId());
        reservation.reject();
        reservation = reservationRepository.save(reservation);

        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 예약 내역 확인
     */
    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }
}
