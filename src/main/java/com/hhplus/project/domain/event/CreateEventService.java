package com.hhplus.project.domain.event;

import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEventService {

    private final EventRepository repository;

    public CreateEvent.Domain createEvent(CreateEvent.Command command){
        return repository.create(Event.toEntity(command)).toDomain();
    }
}
