package com.hhplus.project.domain.event;

import com.hhplus.project.support.BaseException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public record CreateEvent() {
    public record Command(
            @NotNull(message = "Category ID is required")
            Long categoryId,

            @NotNull(message = "Location ID is required")
            Long locationId,

            @NotBlank(message = "Name is required")
            @Size(max = 255, message = "Name must be less than 255 characters")
            String name,

            @NotBlank(message = "Content is required")
            @Size(min = 30, message = "Content must be at least 30 characters long")
            String content,

            @NotNull(message = "Start time is required")
            LocalDateTime startAt,

            @NotNull(message = "End time is required")
            LocalDateTime endAt,

            @NotNull(message = "Host ID is required")
            Long hostId,

            @NotNull(message = "Capacity is required")
            @Min(value = 1, message = "Capacity must be at least 1")
            Integer capacity,

            @NotNull(message = "Approve type is required")
            Boolean isApprove,

            @NotNull(message = "Online status is required")
            Boolean isOnline,

            @NotBlank(message = "Location detail is required")
            String locationDetail,

            RecurringRules rule
    ) {
        private static final Pattern TITLE_REGEX = Pattern.compile(
                "^[가-힣A-Za-z0-9_\\-\\(\\)!?,\\[\\]@#\\$%\\^&\\*\\uD83C-\\uDBFF\\uDC00-\\uDFFF]{2,}$"
        );


        public static Command create(
                Long categoryId,
                Long locationId,
                String name,
                String content,
                LocalDateTime startAt,
                LocalDateTime endAt,
                Long hostId,
                Integer capacity,
                Boolean isApprove,
                Boolean isOnline,
                String locationDetail,
                RecurringRules rule
        ) {

            if (!TITLE_REGEX.matcher(name).matches()) {
                throw new BaseException(EventException.TITLE_REGEX);
            }

            if (ChronoUnit.HOURS.between(startAt, endAt) < 1) {
                throw new BaseException(EventException.WRONG_TIME_SETTING);
            }

            return new Command(
                    categoryId,
                    locationId,
                    name,
                    content,
                    startAt,
                    endAt,
                    hostId,
                    capacity,
                    isApprove,
                    isOnline,
                    locationDetail,
                    rule
            );
        }
    }


    public record Domain(Long eventId){}
}
