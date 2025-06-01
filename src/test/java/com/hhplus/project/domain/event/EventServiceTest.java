package com.hhplus.project.domain.event;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.domain.member.Member;
import com.hhplus.project.fixture.EventFixture;
import com.hhplus.project.fixture.MemberFixture;
import com.hhplus.project.support.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.hhplus.project.fixture.EventFixture.createEvent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventServiceTest extends BaseIntegrationTest {

    @Autowired
    EventFixture eventFixture;

    @Autowired
    MemberFixture memberFixture;

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("이벤트를 조회하면 이벤트가 조회된다")
    void getEvent() {
        // given
        Event newEvent = eventRepository.save(createEvent().toDomain());

        // when
        long eventId = newEvent.eventId();
        Event event = eventService.getEvent(eventId);

        // then
        assertThat(event).isEqualTo(newEvent);
    }

    @Test
    @DisplayName("이벤트가 없으면 예외를 반환한다.")
    void getEvent_exception() {
        // given
        long eventId = 1L;

        // when

        // then
        assertThatThrownBy(() -> eventService.getEvent(eventId))
                .isInstanceOf(BaseException.class)
                .hasMessage(EventException.EVENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("이벤트 목록을 조회 한다.")
    void getEvents() {
        // given
        Member member = memberFixture.create();
        Event event = eventFixture.create(member.memberId());

        EventList.Command command = new EventList.Command(null,
                null,
                null,
                null,
                null,
                EventEnums.SortType.NEW,
                PageRequest.of(0, 10));

        // when
        Page<Event> eventList = eventService.findEventList(command);

        // then
        assertThat(eventList.getTotalElements()).isEqualTo(1L);
        assertThat(eventList.getContent().getFirst()).isEqualTo(event);
    }
}
