package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository jpaRepository;

    @Override
    public Event create(Event event) {
        return jpaRepository.save(event);
    }

    @Override
    public List<Event> createList(List<Event> eventList) {
        return jpaRepository.saveAll(eventList);
    }
}
