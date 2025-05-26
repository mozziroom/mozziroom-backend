package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventException;
import com.hhplus.project.domain.event.EventList;
import com.hhplus.project.domain.event.EventRepository;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    @Override
    public Event getEvent(Long eventId) {
        EventEntity eventEntity = eventJpaRepository.findById(eventId).orElseThrow(() -> new BaseException(EventException.NOT_EXISTS_EVENT));
        return eventEntity.toDomain();
    }

    @Override
    public Page<Event> findEventList(EventList.Command pageable) {
        return eventJpaRepository.findEventList(pageable);
    }

    @Override
    public Event save(Event event) {
        EventEntity eventEntity = eventJpaRepository.findById(event.eventId()).orElseThrow(() -> new BaseException(EventException.NOT_EXISTS_EVENT));
        eventEntity.update(event, null);
        return eventJpaRepository.save(eventEntity).toDomain();
    }
}
