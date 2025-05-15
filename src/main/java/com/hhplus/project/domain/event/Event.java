package com.hhplus.project.domain.event;

import java.time.LocalDateTime;

public record Event(
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
        RecurringRules recurringRules
) {

}
