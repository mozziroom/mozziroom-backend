package com.hhplus.project.domain.reservation;

import com.hhplus.project.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "reservation_history")
@Getter
public class ReservationHistory extends BaseTimeEntity {
    /** 예약 내역 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_history_id")
    private Long reservationHistoryId;

    /** 예약 id */
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    /** 예약 상태 (승인대기, 승인, 취소, 거절) */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationEnums.Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationHistory that = (ReservationHistory) o;
        return Objects.equals(reservationHistoryId, that.reservationHistoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationHistoryId);
    }
}
