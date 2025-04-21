package com.hhplus.project.domain.event;

import com.hhplus.project.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recurring_rules")
@Getter
public class RecurringRules extends BaseTimeEntity {
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
}
