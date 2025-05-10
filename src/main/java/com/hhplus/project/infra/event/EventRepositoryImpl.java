package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository jpaRepository;

    @Override
    public Event create(Event event) {
        return jpaRepository.save(event);
    }
}
