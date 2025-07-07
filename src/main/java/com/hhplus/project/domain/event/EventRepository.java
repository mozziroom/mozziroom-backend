package com.hhplus.project.domain.event;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EventRepository {

    Page<Event> findEventList(EventList.Command pageable);

    Event save(Event event);

    Event getEvent(Long eventId);

    Event findEventWithLock(Long eventId);

    Optional<Location> findLocation(Long locationId);

    Optional<Category> findCategory(Long categoryId);

    EventTimeSlot findEventTimeSlot(Long eventId);

    TimeSlot getTimeSlot(Long timeSlotId);

    Category save(Category category);

    Location save(Location location);

    EventTimeSlot save(EventTimeSlot eventTimeSlot);

    TimeSlot save(TimeSlot timeSlot);
}
