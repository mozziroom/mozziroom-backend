package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.CreateEvent;
import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.support.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event getEvent(long eventId) {
        return eventRepository.getEvent(eventId);
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

    public CreateEvent.Info create(CreateEvent.Command command) {
        if (eventRepository.findCategory(command.categoryId()).isEmpty()) {
            throw new BaseException(EventException.CATEGORY_NOT_FOUND);
        }

        if (eventRepository.findLocation(command.locationId()).isEmpty()) {
            throw new BaseException(EventException.LOCATION_NOT_FOUND);
        }
        return CreateEvent.Info.fromDomain(eventRepository.save(command.toDomain()));
    }
}
