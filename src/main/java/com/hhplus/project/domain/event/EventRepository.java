package com.hhplus.project.domain.event;

import java.util.List;


public interface EventRepository {
    List<Event> create(List<Event> events);
}