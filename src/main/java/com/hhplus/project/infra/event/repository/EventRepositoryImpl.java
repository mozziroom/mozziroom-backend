package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventRepository;
import com.hhplus.project.infra.event.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository jpaRepository;

    @Override
    public Event findById(Long eventId) {
        EventEntity eventEntity = jpaRepository.findById(eventId).orElseThrow(RuntimeException::new);
        return eventEntity.toDomain();
    }

    @Override
    public void save(Event event) {
        jpaRepository.save(EventEntity.fromDomain(event));
    }
}
