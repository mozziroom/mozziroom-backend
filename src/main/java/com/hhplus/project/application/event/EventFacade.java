package com.hhplus.project.application.event;

import com.hhplus.project.application.event.dto.CreateEventFacade;
import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventService;
import com.hhplus.project.domain.reservation.Reservation;
import com.hhplus.project.domain.reservation.ReservationEnums;
import com.hhplus.project.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;
    private final ReservationService reservationService;

    public EventDetailResult.EventDetail getEvent(long eventId, long memberId) {
        Event event = eventService.getEvent(eventId);

        Reservation reservation = reservationService.getReservation(eventId, memberId);
        ReservationEnums.Status status = reservation == null ? ReservationEnums.Status.NONE : reservation.getStatus();

        return EventDetailResult.EventDetail.from(event, memberId, status);
    }

    public Page<EventResult.Events> findEventList(EventCriteria.Events criteria) {
        Page<Event> events = eventService.findEventList(criteria.toCommand());
        return events.map(EventResult.Events::from);
    }

    public CreateEventFacade.Result createEvent(CreateEventFacade.Criteria criteria){

        // GetHostId - memberId 조회하는 기능 추가
        Long memberId = 1L;
        return CreateEventFacade.Result.fromInfo(eventService.create(criteria.toCommand(memberId)));
    }
}
