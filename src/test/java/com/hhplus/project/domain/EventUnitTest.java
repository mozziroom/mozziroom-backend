package com.hhplus.project.domain;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.EventException;
import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.support.BaseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventUnitTest {

    @Test
    @DisplayName("이벤트 정보 변경 시 종료된 이벤트라면 예외를 발생한다")
    public void updateEndedEvent() {
        // given
        Event event = Event.create(
                1L,
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

        UpdateEvent.Command command = UpdateEvent.Command.create(
                1L,
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
        Assertions.assertThatThrownBy(() -> event.update(command, null))
                .isInstanceOf(BaseException.class)
                .hasMessage(EventException.ALREADY_ENDED_EVENT.getMessage());
    }

    @Test
    @DisplayName("이벤트 정보 변경 시 종료된 이벤트가 아니라면 바뀐 이벤트 정보를 세팅하여 반환한다")
    public void updateEvent() {
        Event event = Event.create(
                1L,
                1L,
                1L,
                "서각코 모집",
                "스타벅스에서 모각코 하실 분!",
                LocalDateTime.of(2999, 12, 31, 14, 0),
                LocalDateTime.of(2999, 12, 31, 16, 0),
                1L,
                30,
                EventEnums.ApproveType.AUTO,
                false,
                "스타벅스 XX지점",
                null,
                null
        );

        // given
        UpdateEvent.Command command = UpdateEvent.Command.create(
                1L,
                2L,
                "서각코 모집(장소변경)",
                "메가커피에서 모각코 하실 분!",
                LocalDateTime.of(2999, 12, 31, 20, 0),
                LocalDateTime.of(2999, 12, 31, 21, 0),
                50,
                EventEnums.ApproveType.AUTO,
                false,
                2L,
                "메가커피 **지점",
                null,
                null
        );

        // when
        Event updatedEvent = event.update(command, null);

        // then
        assertThat(updatedEvent)
                .extracting("categoryId", "name", "content", "startAt", "endAt", "capacity", "locationId", "locationDetail")
                .containsExactly(command.categoryId(), command.name(), command.content(), command.startAt(), command.endAt(), command.capacity(), command.locationId(), command.locationDetail());
    }
}
