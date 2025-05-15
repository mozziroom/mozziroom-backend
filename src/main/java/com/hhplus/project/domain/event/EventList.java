package com.hhplus.project.domain.event;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public record EventList(

) {

    public record Command(
            String keyword,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Long locationId,
            Long categoryId,
            EventEnums.SortType sortType,
            Pageable pageable
    ) {

    }
}
