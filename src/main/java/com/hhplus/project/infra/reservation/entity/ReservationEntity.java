package com.hhplus.project.infra.reservation.entity;

import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.reservation.ReservationEnums;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "reservation")
public class ReservationEntity extends BaseTimeEntity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }
}
