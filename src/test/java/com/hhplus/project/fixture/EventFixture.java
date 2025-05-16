package com.hhplus.project.fixture;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.infra.event.entity.EventEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventFixture {

    public static EventEntity createEvent() {
        return EventEntity.builder()
                .categoryId(1L)
                .locationId(1L)
                .name("29cm 투어")
                .content("허재와 함께하는 29투어입니다.")
                .startAt(LocalDateTime.of(LocalDate.of(2025,5,5), LocalTime.of(18,0,0)))
                .endAt(LocalDateTime.of(LocalDate.of(2025,5,5), LocalTime.of(23,59,59)))
                .hostId(1L)
                .capacity(10)
                .approveType(EventEnums.ApproveType.MANUAL)
                .hostId(1L)
                .isOnline(false)
                .locationDetail("29cm 앞")
                .build();
    }

    public static EventEntity creatEventWithHost(long hostId) {
        return EventEntity.builder()
                .categoryId(1L)
                .locationId(1L)
                .name("29cm 투어")
                .content("허재와 함께하는 29투어입니다.")
                .startAt(LocalDateTime.of(LocalDate.of(2025,5,5), LocalTime.of(18,0,0)))
                .endAt(LocalDateTime.of(LocalDate.of(2025,5,5), LocalTime.of(23,59,59)))
                .hostId(1L)
                .capacity(10)
                .approveType(EventEnums.ApproveType.MANUAL)
                .hostId(hostId)
                .isOnline(false)
                .locationDetail("29cm 앞")
                .build();
    }


}
