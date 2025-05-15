package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.Event;

import java.time.LocalDateTime;

public record EventResult(

) {

    public record Events(
            Long eventId,
            String thumbnailImagePath,
            String name,
            Location location,
            Category category,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {

        public record Location(
                String city,
                String district,
                String neighborhood,
                String locationDetail
        ) {
            private static Location from() {
                return new Location(null, null, null, null);
            }
        }

        public record Category(
                String fullName
        ) {
            private static Category from() {
                return new Category(null);
            }
        }

        public static Events from(Event event) {
            return new Events(
                    event.eventId(),
                    null,
                    event.name(),
                    Location.from(),
                    Category.from(),
                    event.startAt(),
                    event.endAt()
            );
        }
    }
}
