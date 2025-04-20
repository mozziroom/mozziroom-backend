package com.hhplus.project.domain.event;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Getter
public class Event {
    /** 스터디 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    /** 스터디명 */
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    /** 스터디 시작시간 */
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    /** 스터디 종료시간 */
    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    /** 스터디 주최자 (member_id) */
    @Column(name = "host_id", nullable = false)
    private Long hostId;

    /** 정원 */
    @Column(name = "capacity", nullable = false)
    private int capacity;

    /** 장소 */
    @Column(name = "place", nullable = false)
    private String place;

    /** 이벤트 승인 타입 */
    @Column(name = "approve_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventEnums.ApproveType approveType;

    /** 스터디 반복 규칙 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_rules_id")
    private RecurringRules recurringRules;

    /** 생성일시 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 수정일시 */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** 삭제일시 */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
