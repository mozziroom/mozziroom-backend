package com.hhplus.project.domain.event;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.hhplus.project.support.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.hhplus.project.fixture.EventFixture.createEvent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventServiceTest extends BaseIntegrationTest {

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("이벤트를 조회하면 이벤트가 조회된다")
    void getEvent() {
        // given
        EventEntity eventEntity = eventRepository.save(createEvent());

        // when
        long eventId = eventEntity.getEventId();
        Event event = eventService.getEvent(eventId);

        // then
        assertThat(event).isEqualTo(eventEntity.toDomain());
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
}
