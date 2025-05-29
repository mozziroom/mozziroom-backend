package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.EventList;
import com.hhplus.project.domain.event.EventEnums;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public record EventListCriteria(

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

        public EventList.Command toCommand() {
            return new EventList.Command(keyword,
                    startAt,
                    endAt,
                    locationId,
                    categoryId,
                    sortType,
                    pageable);
        }
    }

}
