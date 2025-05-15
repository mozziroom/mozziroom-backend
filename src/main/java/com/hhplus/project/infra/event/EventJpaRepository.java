package com.hhplus.project.infra.event;

import com.hhplus.project.infra.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long>, EventRepositoryCustom {

}
