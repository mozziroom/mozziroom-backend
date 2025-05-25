package com.hhplus.project.domain;

import com.hhplus.project.BaseIntegrationTest;
import com.hhplus.project.domain.event.*;
import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.hhplus.project.infra.event.repository.EventJpaRepository;
import com.hhplus.project.support.BaseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventServiceTest extends BaseIntegrationTest {

    @Autowired
    EventService eventService;

    @Autowired
    EventJpaRepository eventJpaRepository;

    @Test
    @DisplayName("이벤트 정보 변경 시 종료된 이벤트라면 예외를 발생한다")
    void updateEndedEvent() {
        // given
        EventEntity event = EventEntity.create(
                1L,
                1L,
                "서각코 모집",
                "스타벅스에서 모각코 하실 분!",
                LocalDateTime.of(2025, 4, 10, 14, 0),
                LocalDateTime.of(2025, 4, 10, 16, 0),
                1L,
                30,
                EventEnums.ApproveType.AUTO,
                false,
                "스타벅스 XX지점",
                null,
                null
        );
        Event savedEvent = eventJpaRepository.save(event).toDomain();

        UpdateEvent.Command command = UpdateEvent.Command.create(
                savedEvent.eventId(),
                1L,
                "서각코 모집(장소변경)",
                "메가커피에서 모각코 하실 분!",
                LocalDateTime.of(2025, 4, 10, 14, 0),
                LocalDateTime.of(2025, 4, 10, 16, 0),
                50,
                EventEnums.ApproveType.AUTO,
                false,
                1L,
                "메가커피 **지점",
                null,
                null
        );

        // when // then
        Assertions.assertThatThrownBy(() -> eventService.update(command))
                .isInstanceOf(BaseException.class)
                .hasMessage(EventException.ALREADY_ENDED_EVENT.getMessage());
    }

    @Test
    @DisplayName("이벤트 정보 변경 시 종료된 이벤트가 아니라면 이벤트 정보를 업데이트한다")
    public void updateEvent() {
        // given
        EventEntity event = EventEntity.create(
                1L,
                1L,
                "서각코 모집",
                "스타벅스에서 모각코 하실 분!",
                LocalDateTime.of(2025, 12, 31, 14, 0),
                LocalDateTime.of(2025, 12, 31, 16, 0),
                1L,
                30,
                EventEnums.ApproveType.AUTO,
                false,
                "스타벅스 XX지점",
                null,
                null
        );
        Event savedEvent = eventJpaRepository.save(event).toDomain();

        UpdateEvent.Command command = UpdateEvent.Command.create(
                savedEvent.eventId(),
                1L,
                "서각코 모집(장소변경)",
                "메가커피에서 모각코 하실 분!",
                LocalDateTime.of(2025, 12, 31, 20, 0),
                LocalDateTime.of(2025, 12, 31, 22, 0),
                50,
                EventEnums.ApproveType.AUTO,
                false,
                1L,
                "메가커피 **지점",
                null,
                null
        );

        eventService.update(command);

        // when
        EventEntity eventEntity = eventJpaRepository.findById(savedEvent.eventId()).get();

        // then
        assertThat(eventEntity)
                .extracting("eventId", "categoryId", "name", "content", "startAt", "endAt", "capacity", "locationId", "locationDetail")
                 .containsExactly(command.eventId(), command.categoryId(), command.name(), command.content(), command.startAt(), command.endAt(), command.capacity(), command.locationId(), command.locationDetail());
    }



}
