package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventList;
import org.springframework.data.domain.Page;

public interface EventRepositoryCustom {

    Page<Event> findEventList(EventList.Command command);
}
