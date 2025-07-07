package com.hhplus.project.infra.event.repository;

import com.hhplus.project.infra.event.entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotJpaRepository extends JpaRepository<TimeSlotEntity, Long> {
}
