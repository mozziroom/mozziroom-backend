package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.*;
import com.hhplus.project.infra.event.entity.CategoryEntity;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.hhplus.project.infra.event.entity.LocationEntity;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    @Override
    public Event getEvent(Long eventId) {
        EventEntity eventEntity = eventJpaRepository.findById(eventId).orElseThrow(() -> new BaseException(EventException.NOT_EXISTS_EVENT));
        return eventEntity.toDomain();
    }

    @Override
    public Event findEventWithLock(Long eventId) {
        return eventJpaRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new BaseException(EventException.NOT_EXISTS_EVENT)).toDomain();
    }

    @Override
    public Optional<Location> findLocation(Long locationId) {
        return locationJpaRepository.findById(locationId).map(LocationEntity::toDomain);
    }

    @Override
    public Optional<Category> findCategory(Long categoryId) {
        return categoryJpaRepository.findById(categoryId).map(CategoryEntity::toDomain);
    }

    @Override
    public Page<Event> findEventList(EventList.Command pageable) {
        return eventJpaRepository.findEventList(pageable);
    }

    @Override
    public Event save(Event event) {
        EventEntity eventEntity = eventJpaRepository.findById(event.eventId()).orElseThrow(() -> new BaseException(EventException.NOT_EXISTS_EVENT));
        eventEntity.update(event, null);
        return eventJpaRepository.save(eventEntity).toDomain();
    }

    @Override
    public Event create(Event event) {
        return eventJpaRepository.save(EventEntity.fromDomain(event,null)).toDomain();
    }
}
