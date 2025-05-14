package com.hhplus.project.domain.event;


import com.hhplus.project.support.BaseException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

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
    private static final Pattern TITLE_REGEX = Pattern.compile(
            "^[가-힣A-Za-z0-9_\\-\\(\\)!?,\\[\\]@#\\$%\\^&\\*\\uD83C-\\uDBFF\\uDC00-\\uDFFF]{2,}$"
    );

    public Event{
        if (name == null || !TITLE_REGEX.matcher(name).matches()) {
            throw new BaseException(EventException.TITLE_REGEX);
        }
        if (startAt != null && endAt != null && ChronoUnit.HOURS.between(startAt, endAt) < 1 ) {
            throw new BaseException(EventException.WRONG_TIME_SETTING);
        }
        if ( capacity > 30 || capacity < 1 ){
            throw new BaseException(EventException.WRONG_CAPACITY);
        }
    }
}
