package com.hhplus.project.domain.event;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Event(
        Long eventId,
        Long categoryId,
        Long locationId,
        String locationDetail,
        String name,
        String content,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Long hostId,
        int capacity,
        EventEnums.ApproveType approveType,
        boolean isOnline,
        RecurringRules recurringRules
) {
}
