package com.hhplus.project.infra.event.entity;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.event.EventEnums;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventEntity extends BaseTimeEntity {
    /** 스터디 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    /** 카테고리 id */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /** 지역 id */
    @Column(name = "location_id")
    private Long locationId;

    /** 스터디명 */
    @Column(name = "name", nullable = false)
    private String name;

    /** 스터디 내용 */
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
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
    
    /** 상세 장소 */
    @Column(name = "location_detail", nullable = false)
    private String locationDetail;

    /** 스터디 반복 규칙 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_rules_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RecurringRulesEntity recurringRules;

    /** 삭제일시 */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private EventEntity(Long eventId,
                       Long categoryId,
                       Long locationId,
                       String name,
                       String content,
                       LocalDateTime startAt,
                       LocalDateTime endAt,
                       Long hostId,
                       int capacity,
                       EventEnums.ApproveType approveType,
                       boolean isOnline,
                       String locationDetail,
                       RecurringRulesEntity recurringRules,
                       LocalDateTime deletedAt
    ) {
        this.eventId = eventId;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.name = name;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.hostId = hostId;
        this.capacity = capacity;
        this.approveType = approveType;
        this.isOnline = isOnline;
        this.locationDetail = locationDetail;
        this.recurringRules = recurringRules;
        this.deletedAt = deletedAt;
    }

    public static EventEntity create(
            Long eventId,
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Long hostId,
            int capacity,
            EventEnums.ApproveType approveType,
            boolean isOnline,
            String locationDetail,
            RecurringRulesEntity recurringRules,
            LocalDateTime deletedAt
    ) {
        return new EventEntity(
                eventId,
                categoryId,
                locationId,
                name,
                content,
                startAt,
                endAt,
                hostId,
                capacity,
                approveType,
                isOnline,
                locationDetail,
                recurringRules,
                deletedAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity event = (EventEntity) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    public Event toDomain() {
        return Event.create(
                this.eventId,
                this.categoryId,
                this.locationId,
                this.name,
                this.content,
                this.startAt,
                this.endAt,
                this.hostId,
                this.capacity,
                this.approveType,
                this.isOnline,
                this.locationDetail,
                this.recurringRules,
                this.deletedAt
        );
    }

    public static EventEntity fromDomain(Event event) {
        return EventEntity.create(
                event.getEventId(),
                event.getCategoryId(),
                event.getLocationId(),
                event.getName(),
                event.getContent(),
                event.getStartAt(),
                event.getEndAt(),
                event.getHostId(),
                event.getCapacity(),
                event.getApproveType(),
                event.isOnline(),
                event.getLocationDetail(),
                event.getRecurringRules(),
                event.getDeletedAt()
        );
    }
}
