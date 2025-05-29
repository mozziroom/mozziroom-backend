package com.hhplus.project.domain.event.dto;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;

import java.time.LocalDateTime;

public record CreateEvent() {

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
            String locationDetail
    )
    {
        public Event toDomain(){
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
                    null,
                    null
            );
        }
    }

    public record Info(Long eventId){
        public static Info fromDomain(Event event){
            return new Info(event.eventId());
        }
    }
}
