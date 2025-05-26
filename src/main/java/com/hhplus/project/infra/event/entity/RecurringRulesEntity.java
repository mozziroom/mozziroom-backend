package com.hhplus.project.infra.event.entity;

import com.hhplus.project.domain.event.RecurringRules;
import com.hhplus.project.infra.BaseTimeEntity;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "recurring_rules")
public class RecurringRulesEntity extends BaseTimeEntity {
    /** 스터디 반복 규칙 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recurring_rules_id")
    private Long recurringRulesId;

    /** 반복 타입 (일간, 주간, 월간, 년간) */
    @Column(name = "recurring_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecurringRulesEnums.Type recurringType;

    /** 주기 */
    @Column(name = "recurring_interval", nullable = false)
    private int recurringInterval;

    /** 반복 시작일 */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** 반복 종료일 */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /** 삭제일시 */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private RecurringRulesEntity(Long recurringRulesId,
                                 RecurringRulesEnums.Type recurringType,
                                 int recurringInterval,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 LocalDateTime deletedAt
    ) {
        this.recurringRulesId = recurringRulesId;
        this.recurringType = recurringType;
        this.recurringInterval = recurringInterval;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deletedAt = deletedAt;
    }
    public static RecurringRulesEntity create(
            Long recurringRulesId,
            RecurringRulesEnums.Type recurringType,
            int recurringInterval,
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime deletedAt
    ) {
        return new RecurringRulesEntity(
                recurringRulesId,
                recurringType,
                recurringInterval,
                startDate,
                endDate,
                deletedAt
        );
    }

    public RecurringRules toDomain() {
        return RecurringRules.create(
                this.recurringRulesId,
                this.recurringType,
                this.recurringInterval,
                this.startDate,
                this.endDate,
                this.deletedAt
        );
    }

    public static RecurringRulesEntity fromDomain(RecurringRules recurringRules) {
        return RecurringRulesEntity.create(
                recurringRules.recurringRulesId(),
                recurringRules.recurringType(),
                recurringRules.recurringInterval(),
                recurringRules.startDate(),
                recurringRules.endDate(),
                recurringRules.deletedAt()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringRulesEntity that = (RecurringRulesEntity) o;
        return Objects.equals(recurringRulesId, that.recurringRulesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recurringRulesId);
    }

}
