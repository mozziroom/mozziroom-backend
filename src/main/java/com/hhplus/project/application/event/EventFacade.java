package com.hhplus.project.application.event;

import com.hhplus.project.application.event.dto.CreateEventFacade;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;

    public GetEvent.Result getEvent(long eventId, long memberId) {
        Event event = eventService.getEvent(eventId);
        return GetEvent.Result.fromDomain(event, memberId);
    }

    public Page<EventResult.Events> findEventList(EventCriteria.Events criteria) {
        Page<Event> events = eventService.findEventList(criteria.toCommand());
        return events.map(EventResult.Events::from);
    }
    
    public CreateEventFacade.Result createEvent(CreateEventFacade.Criteria criteria){

        // GetHostId
        Long memberId = 1L;
        return CreateEventFacade.Result.fromInfo(eventService.create(criteria.toCommand(memberId)));
    }
}
