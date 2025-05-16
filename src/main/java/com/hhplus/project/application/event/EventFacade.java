package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;

    public GetEvent.Result getEvent(long eventId, long memberId) {
        Event event = eventService.getEvent(eventId);
        return GetEvent.Result.fromDomain(event, memberId);
    }
}
