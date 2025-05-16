package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.EventRepository;
import com.hhplus.project.infra.event.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Optional<EventEntity> findById(long eventId) {
        return eventJpaRepository.findById(eventId);
    }

    @Override
    public EventEntity save(EventEntity eventEntity) {
        return eventJpaRepository.save(eventEntity);
    }
}
