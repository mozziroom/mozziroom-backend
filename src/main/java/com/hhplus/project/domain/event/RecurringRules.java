package com.hhplus.project.domain.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecurringRules(
        Long recurringRulesId,
        RecurringRulesEnums.Type recurringType,
        int recurringInterval,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime deletedAt
) {
    public static RecurringRules create(Long recurringRulesId, RecurringRulesEnums.Type recurringType, int recurringInterval, LocalDate startDate, LocalDate endDate, LocalDateTime deletedAt) {
        return new RecurringRules(
                recurringRulesId,
                recurringType,
                recurringInterval,
                startDate,
                endDate,
                deletedAt
        );
    }
}
