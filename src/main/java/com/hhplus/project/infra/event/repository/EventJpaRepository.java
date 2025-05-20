package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.infra.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
    EventEntity save(EventEntity eventEntity);
}
