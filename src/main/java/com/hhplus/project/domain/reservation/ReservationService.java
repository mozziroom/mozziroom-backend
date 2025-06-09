package com.hhplus.project.domain.reservation;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.EventRepository;
import com.hhplus.project.domain.reservation.dto.CreateReservationCommand;
import com.hhplus.project.domain.reservation.dto.UpdateReservationCommand;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationHistoryRepository reservationHistoryRepository;

    /**
     * 이벤트 예약 신청
     */
    @Transactional
    public Reservation reserve(CreateReservationCommand.Command command) {
        // 이벤트 예약 신청
        Reservation reservation = command.toDomain();

        if (command.approveType().equals(EventEnums.ApproveType.MANUAL)) {
            reservation.pending();
        } else {
            reservation.approve();
        }

        reservation = reservationRepository.save(reservation);
        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 이벤트 예약 취소(예약자 본인 취소)
     */
    @Transactional
    public Reservation cancel(UpdateReservationCommand.Command command) {
        Reservation reservation = findReservation(command.reservationId(), ReservationEnums.Status.CANCELED);
        reservation.cancel();
        reservation = reservationRepository.save(reservation);

        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 이벤트 예약 승인(주최자 승인)
     */
    @Transactional
    public Reservation approve(UpdateReservationCommand.Command command) {
        Reservation reservation = findReservation(command.reservationId(), ReservationEnums.Status.APPROVED);

        /**
         * 예약 승인 시 이벤트의 예약 가능 인원 감소
         */
        Event event = eventRepository.findEventWithLock(reservation.getEventId());
        Event updatedEvent = event.decreaseCapacity();
        eventRepository.save(updatedEvent);

        reservation.approve();
        reservation = reservationRepository.save(reservation);

        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 이벤트 예약 거절(주최자 거절)
     */
    @Transactional
    public Reservation reject(UpdateReservationCommand.Command command) {
        Reservation reservation = findReservation(command.reservationId(), ReservationEnums.Status.REJECTED);
        reservation.reject();
        reservation = reservationRepository.save(reservation);

        reservationHistoryRepository.save(reservation);

        return reservation;
    }

    /**
     * 이벤트 예약 상세 조회
     */
    @Transactional
    public Reservation findReservation(Long reservationId, ReservationEnums.Status status) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation.isUpdated(status)) {
            throw new BaseException(ReservationException.ALREADY_UPDATED);
        }

        return reservation;
    }

    // TODO - 이벤트 나가기 기능 구현?

    public Reservation getReservation(Long eventId, Long memberId) {
        return reservationRepository.findByEventIdAndMemberId(eventId, memberId);
    }

}
