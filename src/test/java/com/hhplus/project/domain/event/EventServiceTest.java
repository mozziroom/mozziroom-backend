package com.hhplus.project.domain.event;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.domain.member.Member;
import com.hhplus.project.fixture.EventFixture;
import com.hhplus.project.fixture.MemberFixture;
import com.hhplus.project.support.BaseException;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

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

    @Test
    @DisplayName("이벤트 정보 변경 시 종료된 이벤트라면 예외를 발생한다")
    void updateEndedEvent() {
        // given
        Member member = memberFixture.create();
        LocalDateTime startAt = LocalDateTime.of(2025, 5, 28, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 5, 28, 3, 0);
        Event event = eventFixture.createWithDate(member.memberId(), startAt, endAt);

        UpdateEvent.Command command = createUpdateEventCommand(event, startAt, endAt);

        // when // then
        Assertions.assertThatThrownBy(() -> eventService.update(command))
                .isInstanceOf(BaseException.class)
                .hasMessage(EventException.ALREADY_ENDED_EVENT.getMessage());
    }

    @Test
    @DisplayName("이벤트 정보 변경 시 종료된 이벤트가 아니라면 이벤트 정보를 업데이트한다")
    public void updateEvent() {
        // given
        Member member = memberFixture.create();
        LocalDateTime startAt = LocalDateTime.of(2999, 12, 31, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2999, 12, 31, 3, 0);
        Event event = eventFixture.createWithDate(member.memberId(), startAt, endAt);

        LocalDateTime changedStartAt = LocalDateTime.of(2999, 12, 31, 18, 0);
        LocalDateTime changedEndAt = LocalDateTime.of(2999, 12, 31, 19, 0);
        UpdateEvent.Command command = createUpdateEventCommand(event, changedStartAt, changedEndAt);

        eventService.update(command);

        // when
        Event updatedEvent = eventService.getEvent(event.eventId());

        // then
        assertThat(updatedEvent)
                .extracting("eventId", "categoryId", "name", "content", "startAt", "endAt", "capacity", "locationId", "locationDetail")
                .containsExactly(command.eventId(), command.categoryId(), command.name(), command.content(), command.startAt(), command.endAt(), command.capacity(), command.locationId(), command.locationDetail());
    }

    @NotNull
    private static UpdateEvent.Command createUpdateEventCommand(Event event, LocalDateTime startAt, LocalDateTime endAt) {
        return UpdateEvent.Command.create(
                event.eventId(),
                1L,
                "서각코 모집(장소변경)",
                "메가커피에서 모각코 하실 분!",
                startAt,
                endAt,
                30,
                EventEnums.ApproveType.AUTO,
                false,
                1L,
                "메가커피 **지점",
                null,
                null
        );
    }
}
