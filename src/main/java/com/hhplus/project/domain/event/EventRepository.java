package com.hhplus.project.domain.event;

import org.springframework.data.domain.Page;

public interface EventRepository {

    Page<Event> findEventList(EventList.Command pageable);
}
