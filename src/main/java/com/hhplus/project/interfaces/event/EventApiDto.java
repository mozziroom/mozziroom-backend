package com.hhplus.project.interfaces.event;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public interface EventApiDto {

    record RegisterRequest(
            String event_name,
            LocalDateTime startAt,
            LocalDateTime endAt,
            int capacity,
            String place,
            String approveType, // ENUM
            RecurringRulesRegister recurringRules
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record RegisterResponse(
            Long eventId,
            String reason
    ) {
        public static RegisterResponse success(Long eventId) {
            return new RegisterResponse(eventId, null);
        }

        public static RegisterResponse error(String reason) {
            return new RegisterResponse(null, reason);
        }
    }

    record RecurringRulesRegister(
            String recurringType, // ENUM
            int recurring_interval,
            String startDate,
            String endDate
    ) {
    }
}
