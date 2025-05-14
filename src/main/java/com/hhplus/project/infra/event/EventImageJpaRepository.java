package com.hhplus.project.infra.event;

import com.hhplus.project.infra.event.entity.EventImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventImageJpaRepository extends JpaRepository<EventImageEntity,Long> {
}

