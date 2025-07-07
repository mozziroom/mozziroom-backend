package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.*;
import com.hhplus.project.infra.event.entity.*;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final EventTimeSlotJpaRepository eventTimeSlotJpaRepository;
    private final TimeSlotJpaRepository timeSlotJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Event getEvent(Long eventId) {
        return eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(EventException.EVENT_NOT_FOUND)).toDomain();
    }

    @Override
    public Event findEventWithLock(Long eventId) {
        return eventJpaRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new BaseException(EventException.EVENT_NOT_FOUND)).toDomain();
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
    public EventTimeSlot findEventTimeSlot(Long eventId) {
        return eventTimeSlotJpaRepository.findByEventTimeSlotEntityByEvent_eventId(eventId).toDomain();
    }

    @Override
    public TimeSlot getTimeSlot(Long timeSlotId) {
        return timeSlotJpaRepository.findById(timeSlotId)
                .map(TimeSlotEntity::toDomain)
                .orElseThrow(() -> new BaseException(EventException.EVENT_NOT_FOUND));
    }

    @Override
    public Page<Event> findEventList(EventList.Command command) {
        return eventJpaRepository.findEventList(command);
    }

    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(EventEntity.fromDomain(event, null)).toDomain();
    }

    @Override
    public Category save(Category category) {
        return categoryJpaRepository.save(CategoryEntity.fromDomain(category)).toDomain();
    }

    @Override
    public Location save(Location location) {
        return locationJpaRepository.save(LocationEntity.fromDomain(location)).toDomain();
    }

    @Override
    public EventTimeSlot save(EventTimeSlot eventTimeSlot) {
        return eventTimeSlotJpaRepository.save(EventTimeSlotEntity.fromDomain(eventTimeSlot)).toDomain();
    }

    @Override
    public TimeSlot save(TimeSlot timeSlot) {
        return timeSlotJpaRepository.save(TimeSlotEntity.fromDomain(timeSlot)).toDomain();
    }
}
