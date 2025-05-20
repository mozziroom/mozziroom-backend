package com.hhplus.project.domain.event;

public interface EventRepository {

    Event save(Event event);

    Event getEvent(Long aLong);
}
