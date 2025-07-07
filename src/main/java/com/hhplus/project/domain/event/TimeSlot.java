package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.UpdateEvent;

import java.time.LocalTime;

public record TimeSlot(
        Long timeSlotId,
        LocalTime startTime,
        LocalTime endTime
) {
    public TimeSlot update(UpdateEvent.Command command) {
        return new TimeSlot(timeSlotId, command.startTime(), command.endTime());
    }
}
