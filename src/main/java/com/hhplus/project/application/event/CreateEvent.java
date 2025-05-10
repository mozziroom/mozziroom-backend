package com.hhplus.project.application.event;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateEvent() {

    public record Criteria(
            // USER
            String Token,

            // EVENT
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Integer capacity,
            Boolean isApprove,
            Boolean isOnline,
            String locationDetail,

            // RECURRING RULE
            String recurringType,
            Integer recurringInterval,
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime deleteAt,

            // FILE
            MultipartFile thumbNail
    ){

    }

    public record Result(Long eventId){}
}
