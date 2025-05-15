package com.hhplus.project.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Page<Event> findEventList(EventList.Command command) {
        return eventRepository.findEventList(command);
    }
}
