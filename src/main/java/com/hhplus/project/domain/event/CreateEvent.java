package com.hhplus.project.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

// Event 생성
public record CreateEvent(
) {

    public record Command(
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
        public Event toDomain(RecurringRules recurringRules, LocalDateTime startAt, LocalDateTime endAt){
            return new Event(
                    null,
                    categoryId,
                    locationId,
                    name,
                    content,
                    startAt,
                    endAt,
                    hostId,
                    capacity,
                    approveType,
                    isOnline,
                    locationDetail,
                    recurringRules
            );
        }
    }

    public record Domain(List<Long> eventIds){
        public static CreateEvent.Domain fromDomain(List<Event> events) {
            return new CreateEvent.Domain(events.stream()
                    .map(Event::eventId)
                    .toList());
        }
    }

}
