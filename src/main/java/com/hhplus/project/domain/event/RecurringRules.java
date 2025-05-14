package com.hhplus.project.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record RecurringRules(
        Long recurringRulesId,
        RecurringRulesEnums.Type recurringType,
        int recurringInterval,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime deletedAt
) {
    public RecurringRules {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate는 endDate보다 이후일 수 없습니다.");
        }

        long gapDays   = ChronoUnit.DAYS.between(startDate, endDate);
        long gapMonths = ChronoUnit.MONTHS.between(startDate, endDate);
        long gapYears  = ChronoUnit.YEARS.between(startDate, endDate);

        switch (recurringType) {
            case DAY -> {
                if (gapDays < recurringInterval) {
                    throw new IllegalArgumentException("DAY 타입은 간격이 " + recurringInterval + "일 이상이어야 합니다.");
                }
            }
            case WEEK -> {
                if (gapDays < recurringInterval * 7L) {
                    throw new IllegalArgumentException("WEEK 타입은 간격이 " + (recurringInterval * 7) + "일 이상이어야 합니다.");
                }
            }
            case MON -> {
                if (gapMonths < recurringInterval) {
                    throw new IllegalArgumentException("MON 타입은 간격이 " + recurringInterval + "개월 이상이어야 합니다.");
                }
            }
            case YEAR -> {
                if (gapYears < recurringInterval) {
                    throw new IllegalArgumentException("YEAR 타입은 간격이 " + recurringInterval + "년 이상이어야 합니다.");
                }
            }
            default -> throw new IllegalArgumentException("지원하지 않는 반복 타입입니다.");
        }
    }
}