package com.hhplus.project.infra.event.entity;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRules;
import com.hhplus.project.infra.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "event")
public class EventEntity extends BaseTimeEntity {
    /**
     * 스터디 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    /**
     * 카테고리 id
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * 지역 id
     */
    @Column(name = "location_id")
    private Long locationId;

    /**
     * 스터디명
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 스터디 내용
     */
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    /**
     * 스터디 주최자 (member_id)
     */
    @Column(name = "host_id", nullable = false)
    private Long hostId;

    /**
     * 정원
     */
    @Column(name = "capacity", nullable = false)
    private int capacity;

    /**
     * 이벤트 승인 타입
     */
    @Column(name = "approve_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventEnums.ApproveType approveType;

    /**
     * 온라인 여부
     */
    @Column(name = "is_online", nullable = false)
    private boolean isOnline;

    /**
     * 상세 장소
     */
    @Column(name = "location_detail", nullable = false)
    private String locationDetail;

    /**
     * 스터디 반복 규칙
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_rules_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RecurringRulesEntity recurringRules;

    /**
     * 삭제일시
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private EventEntity(Long eventId,
                        Long categoryId,
                        Long locationId,
                        String name,
                        String content,
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
        this.hostId = hostId;
        this.capacity = capacity;
        this.approveType = approveType;
        this.isOnline = isOnline;
        this.locationDetail = locationDetail;
        this.recurringRules = recurringRules;
        this.deletedAt = deletedAt;
    }

    public static EventEntity create(
            Long categoryId,
            Long locationId,
            String name,
            String content,
            Long hostId,
            int capacity,
            EventEnums.ApproveType approveType,
            boolean isOnline,
            String locationDetail,
            RecurringRulesEntity recurringRules,
            LocalDateTime deletedAt
    ) {
        return new EventEntity(
                null,
                categoryId,
                locationId,
                name,
                content,
                hostId,
                capacity,
                approveType,
                isOnline,
                locationDetail,
                recurringRules,
                deletedAt
        );
    }

    public Event toDomain() {
        return Event.create(
                this.eventId,
                this.categoryId,
                this.locationId,
                this.name,
                this.content,
                this.hostId,
                this.capacity,
                this.approveType,
                this.isOnline,
                this.locationDetail,
                null,
                this.deletedAt
        );
    }

    public static EventEntity fromDomain(Event event, RecurringRulesEntity recurringRulesEntity) {
        return new EventEntity(
                event.eventId(),
                event.categoryId(),
                event.locationId(),
                event.name(),
                event.content(),
                event.hostId(),
                event.capacity(),
                event.approveType(),
                event.isOnline(),
                event.locationDetail(),
                recurringRulesEntity,
                event.deletedAt()
        );
    }

    public void update(Event event, RecurringRules recurringRules) {
        // 이벤트 업데이트
        this.categoryId = event.categoryId();
        this.locationId = event.locationId();
        this.name = event.name();
        this.content = event.content();
        this.capacity = event.capacity();
        this.approveType = event.approveType();
        this.isOnline = event.isOnline();
        this.locationDetail = event.locationDetail();
        this.recurringRules = recurringRules == null ?
                null : RecurringRulesEntity.fromDomain(recurringRules);
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
}
