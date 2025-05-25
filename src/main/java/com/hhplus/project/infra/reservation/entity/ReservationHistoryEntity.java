package com.hhplus.project.infra.reservation.entity;

import com.hhplus.project.domain.reservation.Reservation;
import com.hhplus.project.domain.reservation.ReservationHistory;
import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.reservation.ReservationEnums;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reservation_history")
public class ReservationHistoryEntity extends BaseTimeEntity {
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

    private ReservationHistoryEntity(
            Long reservationHistoryId,
            Long reservationId,
            ReservationEnums.Status status
    ){
        this.reservationHistoryId = reservationHistoryId;
        this.reservationId = reservationId;
        this.status = status;
    }

    public static ReservationHistoryEntity create(
            Long reservationHistoryId,
            Long reservationId,
            ReservationEnums.Status status
    ) {
        return new ReservationHistoryEntity(
                reservationHistoryId,
                reservationId,
                status
        );
    }

    public ReservationHistory toDomain() {
        return ReservationHistory.create(
                this.reservationHistoryId,
                this.reservationId,
                this.status
        );
    }

    public static ReservationHistoryEntity fromDomain(Reservation reservation){
        return ReservationHistoryEntity.create(
                null,
                reservation.getReservationId(),
                reservation.getStatus()
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationHistoryEntity that = (ReservationHistoryEntity) o;
        return Objects.equals(reservationHistoryId, that.reservationHistoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationHistoryId);
    }
}
