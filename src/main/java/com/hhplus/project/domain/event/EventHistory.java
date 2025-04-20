package com.hhplus.project.domain.event;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_history")
@Getter
public class EventHistory {
    /** 이벤트 히스토리 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_history_id")
    private Long eventHistoryId;

    /** 스터디 id */
    @Column(name = "event_id")
    private Long eventId;

    /** 스터디명 */
    @Column(name = "name")
    private String name;

    /** 스터디 내용 */
    @Column(name = "content")
    private String content;

    /** 스터디 시작시간 */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /** 스터디 종료시간 */
    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    /** 스터디 주최자 (member_id) */
    @Column(name = "host_id")
    private Long hostId;

    /** 정원 */
    @Column(name = "capacity")
    private int capacity;

    /** 장소 */
    @Column(name = "place")
    private String place;

    /** 이벤트 승인 타입 */
    @Column(name = "approve_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventEnums.ApproveType approveType;

    /** 스터디 반복 규칙 id */
    @Column(name = "recurring_rules_id")
    private Long recurringRulesId;

    /** 생성일시 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 수정일시 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** 삭제일시 */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
