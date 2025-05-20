package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.UpdateEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

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
