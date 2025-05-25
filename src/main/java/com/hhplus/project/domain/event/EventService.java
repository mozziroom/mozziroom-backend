package com.hhplus.project.domain.event;

import com.hhplus.project.infra.event.entity.EventEntity;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    // 이벤트 상세 조회
    public Event getEvent(long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(EventException.EVENT_NOT_FOUND));
        return eventEntity.toDomain();
    }
  
    public Page<Event> findEventList(EventList.Command command) {
        return eventRepository.findEventList(command);
    }
}
