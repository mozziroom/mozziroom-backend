package com.hhplus.project.domain.event;

public interface EventRepository {
    Event findById(Long eventId);

    void save(Event event);
}
