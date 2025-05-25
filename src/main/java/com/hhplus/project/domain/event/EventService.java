package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.UpdateEvent;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Event update(UpdateEvent.Command command) {
        // 이벤트 조회
        Event event = eventRepository.getEvent(command.eventId());
        // 이벤트 도메인 모델 정보 변경
        Event updatedEvent = event.update(command, null);

        // TODO 반복룰 설계 수정 후 구현
        // TODO 파사드에서 이미지 서비스 만들어서 처리하기

        return eventRepository.save(updatedEvent);
    }
}
