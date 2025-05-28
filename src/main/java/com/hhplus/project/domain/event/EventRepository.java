package com.hhplus.project.domain.event;

import java.util.List;
import java.util.Optional;


public interface EventRepository {
    List<Event> create(List<Event> events);

    Optional<Category> findCategory(Long categoryId);

    Optional<Location> findLocation(Long locationId);
}