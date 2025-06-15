package com.hhplus.project.fixture;

import com.hhplus.project.domain.event.*;
import com.hhplus.project.infra.event.entity.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class EventFixture {

    private final EventRepository eventRepository;

    @Transactional(rollbackFor = {Exception.class})
    public Event create(Long hostId) {
        Category category = createCategory();
        Location location = createLocation();

        return eventRepository.save(Event.create(null,
                category.categoryId(),
                location.locationId(),
                "29cm 투어. 👍",
                "허재와 함께하는 29투어입니다.",
                LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(18, 0, 0)),
                LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(23, 59, 59)),
                hostId,
                10,
                EventEnums.ApproveType.MANUAL,
                false,
                "29cm 앞",
                null,
                null
        ));
    }

    @Transactional(rollbackFor = {Exception.class})
    public Event createWithDate(Long hostId, LocalDateTime startAt, LocalDateTime endAt) {
        Category category = createCategory();
        Location location = createLocation();

        return eventRepository.save(Event.create(null,
                category.categoryId(),
                location.locationId(),
                "29cm 투어. 👍",
                "허재와 함께하는 29투어입니다.",
                startAt,
                endAt,
                hostId,
                10,
                EventEnums.ApproveType.MANUAL,
                false,
                "29cm 앞",
                null,
                null
        ));
    }

    @Transactional(rollbackFor = {Exception.class})
    public Category createCategory() {
        return eventRepository.save(Category.create("테스트 카테고리",
                true,
                null,
                1));
    }

    @Transactional(rollbackFor = {Exception.class})
    public Location createLocation() {
        return eventRepository.save(Location.create("TEST",
                "경기도 성남시",
                "황세울로",
                "건물"
        ));
    }

    public static EventEntity createEvent() {
        return EventEntity.builder()
                .categoryId(1L)
                .locationId(1L)
                .name("29cm 투어")
                .content("허재와 함께하는 29투어입니다.")
                .startAt(LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(18, 0, 0)))
                .endAt(LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(23, 59, 59)))
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
                .startAt(LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(18, 0, 0)))
                .endAt(LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(23, 59, 59)))
                .hostId(1L)
                .capacity(10)
                .approveType(EventEnums.ApproveType.MANUAL)
                .hostId(hostId)
                .isOnline(false)
                .locationDetail("29cm 앞")
                .build();
    }

}
