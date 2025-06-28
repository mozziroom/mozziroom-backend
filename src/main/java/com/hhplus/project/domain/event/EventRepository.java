package com.hhplus.project.domain.event;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository {

    Page<Event> findEventList(EventList.Command pageable);

    Event save(Event event);

    Event getEvent(Long eventId);

    Event findEventWithLock(Long eventId);

    Optional<Location> findLocation(Long locationId);

    Optional<Category> findCategory(Long categoryId);

    Category save(Category category);

    Location save(Location location);

    List<Location> saveLocations(List<Location> locationList);

    List<Category> saveCategories(List<Category> categoryList);

    List<Location> findLocation(Set<String> regionCode);

    List<Category> findCategory(Set<Long> categoryIdList);

}
