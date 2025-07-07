package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.UpdateEvent;

import java.time.LocalDate;

public record EventTimeSlot(
        Long eventTimeSlotId,
        Event event,
        LocalDate eventDate,
        Long timeSlotId
) {

    public EventTimeSlot update(UpdateEvent.Command command) {
        return new EventTimeSlot(eventTimeSlotId, event, command.eventDate(), timeSlotId);
    }
}
