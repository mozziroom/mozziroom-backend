package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventRepository;
import com.hhplus.project.infra.event.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository jpaRepository;


    @Override
    public List<Event> create(List<Event> events) {
        List<EventEntity> eventEntities = events.stream()
                .map(EventEntity::fromDomain)
                .toList();

        return jpaRepository.saveAll(eventEntities).stream()
                .map(EventEntity::toDomain)
                .toList();
    }
}
