package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventFacade {
    private final EventService eventService;
    public CreateEvent.Result create(CreateEvent.Criteria criteria){
        return CreateEvent.Result.from(eventService.create(criteria.toCommand()));
    }

}
