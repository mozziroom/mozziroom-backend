package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.EventCommand;
import com.hhplus.project.domain.event.EventEnums;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public record EventCriteria(

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

        public EventCommand.Events toCommand() {
            return new EventCommand.Events(keyword,
                    startAt,
                    endAt,
                    locationId,
                    categoryId,
                    sortType,
                    pageable);
        }
    }

}
