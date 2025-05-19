package com.hhplus.project.domain.reservation;

import lombok.Getter;

@Getter
public class Reservation {
    /** 예약 id */
    private Long reservationId;

    /** 스터디 id */
    private Long eventId;

    /** 예약자 id */
    private Long memberId;

    /** 예약 상태 (승인대기, 승인, 취소, 거절) */
    private ReservationEnums.Status status;

    public static Reservation create(
            Long reservationId,
            Long eventId,
            Long memberId,
            ReservationEnums.Status status
    ) {
        Reservation reservation = new Reservation();
        reservation.reservationId = reservationId;
        reservation.eventId = eventId;
        reservation.memberId = memberId;
        reservation.status = status;

        return reservation;
    }

    public void pending(){
        this.status = ReservationEnums.Status.PENDING;
    }

    public void approve(){
        this.status = ReservationEnums.Status.APPROVED;
    }

    public void reject(){
        this.status = ReservationEnums.Status.REJECTED;
    }

    public void cancel(){
        this.status = ReservationEnums.Status.CANCELED;
    }
}