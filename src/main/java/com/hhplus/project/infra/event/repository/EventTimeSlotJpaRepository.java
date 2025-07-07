package com.hhplus.project.infra.event.repository;

import com.hhplus.project.infra.event.entity.EventTimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTimeSlotJpaRepository extends JpaRepository<EventTimeSlotEntity, Long> {

    EventTimeSlotEntity findByEventTimeSlotEntityByEvent_eventId(long eventId);
}
