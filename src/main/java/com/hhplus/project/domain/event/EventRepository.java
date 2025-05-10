package com.hhplus.project.domain.event;

import java.util.List;

public interface EventRepository {

    Event create(Event event);

    List<Event> createList(List<Event> eventList);
}
