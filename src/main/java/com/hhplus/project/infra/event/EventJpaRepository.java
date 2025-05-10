package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event,Long> {
}
