package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.*;
import com.hhplus.project.domain.reservation.ReservationEnums;
import com.hhplus.project.interfaces.member.MemberResponse;

import java.time.LocalDateTime;
import java.util.List;

public record EventDetailResult(

) {

    public record EventDetail(
            Category category,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Location location,
            List<MemberResponse> participants,
            int capacity,
            boolean isOnline,
            List<EventImage> eventImages,
            RecurringRules recurringRule,
            Boolean isHost,
            ReservationEnums.Status reservationStatus
    ) {

        public record Location(
                String city,
                String district,
                String neighborhood,
                String locationDetail
        ) {
            private static EventDetailResult.EventDetail.Location from() {
                return new EventDetailResult.EventDetail.Location(null, null, null, null);
            }
        }

        public record Category(
                String fullName
        ) {
            private static EventDetailResult.EventDetail.Category from() {
                return new EventDetailResult.EventDetail.Category(null);
            }
        }

        public record EventImage(
                String imageUrl
        ) {
            private static EventDetailResult.EventDetail.Category from() {
                return new EventDetailResult.EventDetail.Category(null);
            }
        }

        public static EventDetail from(Event event, long memberId, ReservationEnums.Status status) {
            Boolean isHost = event.hostId().equals(memberId);
            return new EventDetail(
                    null,
                    event.name(),
                    event.content(),
                    event.startAt(),
                    event.endAt(),
                    null,
                    null,
                    event.capacity(),
                    event.isOnline(),
                    null,
                    null,
                    isHost,
                    status
            );
        }
    }
}
