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

        EventTimeSlot eventTimeSlot = eventRepository.findEventTimeSlot(command.eventId());
        TimeSlot timeSlot = eventRepository.getTimeSlot(eventTimeSlot.timeSlotId());

        eventRepository.save(eventTimeSlot.update(command));
        eventRepository.save(timeSlot.update(command));

        return eventRepository.save(updatedEvent);
    }

    @Transactional
    public CreateEvent.Info create(CreateEvent.Command command) {
        if (eventRepository.findCategory(command.categoryId()).isEmpty()) {
            throw new BaseException(EventException.CATEGORY_NOT_FOUND);
        }

        if (eventRepository.findLocation(command.locationId()).isEmpty()) {
            throw new BaseException(EventException.LOCATION_NOT_FOUND);
        }

        Event event = eventRepository.save(command.toDomain());
        TimeSlot timeSlot = eventRepository.save(command.toTimeSlotDomain());
        eventRepository.save(command.toEventTimeSlotDomain(event, timeSlot));

        return CreateEvent.Info.fromDomain(event);
    }
}
