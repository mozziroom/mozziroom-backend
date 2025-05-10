package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.CreateEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class Event {
    private final CreateEventService createEventService;

    public CreateEvent.Result create(CreateEvent.Criteria criteria){
        return null;
    }
}
