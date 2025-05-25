package com.hhplus.project.domain.event;

import com.hhplus.project.infra.event.entity.EventEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EventRepository {

    Optional<EventEntity> findById(long eventId);

    EventEntity save(EventEntity eventEntity);

    Page<Event> findEventList(EventList.Command pageable);
    
    Event save(Event event);

    Event getEvent(Long eventId);
}
