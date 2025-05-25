package com.hhplus.project.infra.event.entity;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.infra.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
     * 스터디 시작시간
     */
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    /**
     * 스터디 종료시간
     */
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

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
        return new Event(
                this.eventId,
                this.categoryId,
                this.locationId,
                this.locationDetail,
                this.name,
                this.content,
                this.startAt,
                this.endAt,
                this.hostId,
                this.capacity,
                this.approveType,
                this.isOnline,
                this.recurringRules != null ? this.recurringRules.toDomain() : null
        );
    }
}
