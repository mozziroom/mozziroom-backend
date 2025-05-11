package com.hhplus.project.domain.event;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public record EventCommand(
) {

    public record Events(
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
