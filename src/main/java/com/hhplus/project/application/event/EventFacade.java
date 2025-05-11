package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;

    public Page<EventResult.Events> findEventList(EventCriteria.Events criteria) {
        Page<Event> events = eventService.findEventList(criteria.toCommand());
        return events.map(EventResult.Events::from);
    }
}
