package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.*;
import com.hhplus.project.interfaces.event.EventDetail;
import com.hhplus.project.interfaces.member.MemberResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetEvent(

) {

    public record Result(
            Category category,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Location location,
            List<MemberResponse> participants,
            int capacity,
            boolean isOnline,
            List<EventDetail.EventImage> eventImages,
            RecurringRules recurringRule,
            Boolean isHost,
            String reservationStatus
    ) {

        public static Result fromDomain(Event event, long memberId) {
            boolean isHost = event.hostId().equals(memberId);
            return new Result(
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
                    null
            );
        }
    }
}
