package com.hhplus.project.domain.event;

import com.hhplus.project.infra.event.entity.EventEntity;

import java.util.Optional;

public interface EventRepository {

    Optional<EventEntity> findById(long eventId);

    EventEntity save(EventEntity eventEntity);
}
