package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventCommand;
import org.springframework.data.domain.Page;

public interface EventRepositoryCustom {

    Page<Event> findEventList(EventCommand.Events command);
}
