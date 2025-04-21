package com.hhplus.project.domain.reservation;

import com.hhplus.project.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "reservation")
@Getter
public class Reservation extends BaseTimeEntity {
    /** 예약 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    /** 스터디 id */
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    /** 예약자 id */
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    /** 예약 상태 (승인대기, 승인, 취소, 거절) */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationEnums.Status status;
}
