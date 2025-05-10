package com.hhplus.project.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateEventService {

    private final EventRepository repository;

    public CreateEvent.Domain createEvent(CreateEvent.Command command){
        return repository.create(Event.toEntity(command)).toDomain();
    }


    public List<CreateEvent.Domain> createEvent(List<CreateEvent.Command> commands) {
        List<Event> eventList = commands.stream()
                .map(Event::toEntity)
                .toList();

        return repository.createList(eventList).stream()
                .map(Event::toDomain)
                .toList();
    }

}
