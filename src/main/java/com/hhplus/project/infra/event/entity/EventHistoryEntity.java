package com.hhplus.project.infra.event.entity;

import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.event.EventEnums;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "event_history")
public class EventHistoryEntity extends BaseTimeEntity {
    /** 이벤트 히스토리 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_history_id")
    private Long eventHistoryId;

    /** 스터디 id */
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    /** 카테고리 id */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /** 스터디명 */
    @Column(name = "name", nullable = false)
    private String name;

    /** 스터디 내용 */
    @Column(name = "content", nullable = false)
    private String content;

    /** 스터디 시작시간 */
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    /** 스터디 종료시간 */
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    /** 스터디 주최자 (member_id) */
    @Column(name = "host_id", nullable = false)
    private Long hostId;

    /** 정원 */
    @Column(name = "capacity", nullable = false)
    private int capacity;

    /** 이벤트 승인 타입 */
    @Column(name = "approve_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventEnums.ApproveType approveType;

    /** 온라인 여부 */
    @Column(name = "is_online", nullable = false)
    private boolean isOnline;

    /** 지역 id */
    @Column(name = "location_id")
    private Long locationId;

    /** 상세 장소 */
    @Column(name = "location_detail", nullable = false)
    private String locationDetail;

    /** 스터디 반복 규칙 id */
    @Column(name = "recurring_rules_id")
    private Long recurringRulesId;

    /** 삭제일시 */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventHistoryEntity that = (EventHistoryEntity) o;
        return Objects.equals(eventHistoryId, that.eventHistoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventHistoryId);
    }
}
