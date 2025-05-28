package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CreateEvent() {
    public record Criteria(
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Long hostId,
            Integer capacity,
            EventEnums.ApproveType approveType,
            Boolean isOnline,
            String locationDetail,
            RecurringRules recurringRules,
            MultipartFile file
    ){
        public com.hhplus.project.domain.event.CreateEvent.Command toCommand(){
            return new com.hhplus.project.domain.event.CreateEvent.Command(
                    categoryId(),
                    locationId(),
                    name(),
                    content(),
                    startAt(),
                    endAt(),
                    hostId(),
                    capacity(),
                    approveType(),
                    isOnline(),
                    locationDetail(),
                    recurringRules.toDomain(),
                    file()
            );
        }
    }

    public record Result(
            List<Long> eventIds
    ){
        public static CreateEvent.Result from(
                com.hhplus.project.domain.event.CreateEvent.Domain domain
        ){
            return new Result(domain.eventIds());
        }
    }

    public record RecurringRules(
            RecurringRulesEnums.Type recurringType,
            int recurringInterval,
            LocalDate startAt,
            LocalDate endAt
    ) {
        public com.hhplus.project.domain.event.RecurringRules toDomain(){
            return new com.hhplus.project.domain.event.RecurringRules(
                    null,
                    recurringType(),
                    recurringInterval(),
                    startAt(),
                    endAt(),
                    null
            );
        }
    }
}
