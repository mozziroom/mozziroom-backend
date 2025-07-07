package com.hhplus.project.application.event.dto;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.dto.CreateEvent;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateEventFacade() {
    public record Criteria(
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime,
            String token,
            Integer capacity,
            EventEnums.ApproveType approveType,
            Boolean isOnline,
            String locationDetail
    )
    {
        public CreateEvent.Command toCommand(Long hostId){
            return new CreateEvent.Command(
                    categoryId,
                    locationId,
                    name,
                    content,
                    eventDate,
                    startTime,
                    endTime,
                    hostId,
                    capacity,
                    approveType,
                    isOnline,
                    locationDetail
            );
        }
    }

    public record Result(
            Long eventId
    ){
        public static Result fromInfo(CreateEvent.Info info){
            return new Result(info.eventId());
        }
    }

}
