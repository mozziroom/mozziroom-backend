package com.hhplus.project.domain.event.dto;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.EventTimeSlot;
import com.hhplus.project.domain.event.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateEvent() {

    public record Command(
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime,
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
                    hostId,
                    capacity,
                    approveType,
                    isOnline,
                    locationDetail,
                    null,
                    null
            );
        }

        public EventTimeSlot toEventTimeSlotDomain(Event event, TimeSlot timeSlot) {
            return new EventTimeSlot(
                    null,
                    event,
                    eventDate,
                    timeSlot.timeSlotId()
            );
        }

        public TimeSlot toTimeSlotDomain() {
            return new TimeSlot(null, startTime, endTime);
        }
    }

    public record Info(Long eventId){
        public static Info fromDomain(Event event){
            return new Info(event.eventId());
        }
    }
}
