package com.hhplus.project.infra.entity;

import com.hhplus.project.domain.event.Event;
import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.infra.event.entity.EventEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventEntityUnitTest {

    @Test
    @DisplayName("이벤트 도메인 모델 정보로 이벤트 JPA 엔티티 정보를 변경한다")
    public void updateEventEntity() {
        // given
        EventEntity eventEntity = EventEntity.create(
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

        Event event = Event.create(
                1L,
                2L,
                2L,
                "서각코 모집(장소변경)",
                "메가커피에서 모각코 하실 분!",
                LocalDateTime.of(2999, 12, 31, 20, 0),
                LocalDateTime.of(2999, 12, 31, 21, 0),
                1L,
                50,
                EventEnums.ApproveType.AUTO,
                false,
                "메가커피 **지점",
                null,
                null
        );

        // when
        eventEntity.update(event, null);

        // then
        assertThat(eventEntity)
                .extracting("categoryId", "name", "content", "startAt", "endAt", "capacity", "locationId", "locationDetail")
                .containsExactly(event.categoryId(), event.name(), event.content(), event.startAt(), event.endAt(), event.capacity(), event.locationId(), event.locationDetail());

    }
}
