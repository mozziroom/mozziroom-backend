package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventList;
import com.hhplus.project.domain.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Page<Event> findEventList(EventList.Command pageable) {
        return eventJpaRepository.findEventList(pageable);
    }
}
