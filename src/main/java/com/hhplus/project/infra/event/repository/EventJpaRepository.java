package com.hhplus.project.infra.event.repository;

import com.hhplus.project.infra.event.entity.EventEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long>, EventRepositoryCustom {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM EventEntity e WHERE e.eventId = ?1")
    Optional<EventEntity> findByIdWithLock(Long id);

    EventEntity save(EventEntity eventEntity);
}
