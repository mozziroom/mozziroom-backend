package com.hhplus.project.domain.reservation;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_history")
@Getter
public class ReservationHistory {
    /** 예약 내역 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_history_id")
    private Long reservationHistoryId;

    /** 예약 id */
    @Column(name = "reservation_id")
    private Long reservationId;

    /** 예약 상태 (승인대기, 승인, 취소, 거절) */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationEnums.Status status;

    /** 생성일 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 수정일 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
