package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.support.BaseException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public record Event(
        /** 스터디 id */
        Long eventId,
        /** 카테고리 id */
         Long categoryId,
        /** 지역 id */
         Long locationId,
        /** 스터디명 */
         String name,
        /** 스터디 내용 */
         String content,
        /** 스터디 시작시간 */
         LocalDateTime startAt,
        /** 스터디 종료시간 */
         LocalDateTime endAt,
        /** 스터디 주최자 (member_id) */
         Long hostId,
        /** 정원 */
         int capacity,
        /** 이벤트 승인 타입 */
         EventEnums.ApproveType approveType,
        /** 온라인 여부 */
         boolean isOnline,
        /** 상세 장소 */
         String locationDetail,
        /** 스터디 반복 규칙 */
         RecurringRules recurringRules,
        /** 삭제일시 */
         LocalDateTime deletedAt
) {
    private static final Pattern TITLE_REGEX = Pattern.compile(
            "^[가-힣A-Za-z0-9_\\-\\(\\)!?,\\[\\]@#\\$%\\^&\\*\\uD83C-\\uDBFF\\uDC00-\\uDFFF]{2,}$"
    );

    public Event{
        if (name == null || !TITLE_REGEX.matcher(name).matches()) {
            throw new BaseException(EventException.TITLE_REGEX);
        }
        if (startAt != null && endAt != null && ChronoUnit.HOURS.between(startAt, endAt) < 1 ) {
            throw new BaseException(EventException.WRONG_TIME_SETTING);
        }
        if ( capacity > 30 || capacity < 1 ){
            throw new BaseException(EventException.WRONG_CAPACITY);
        }
    }

    public static Event create(
            Long eventId,
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Long hostId,
            int capacity,
            EventEnums.ApproveType approveType,
            boolean isOnline,
            String locationDetail,
            RecurringRules recurringRules,
            LocalDateTime deletedAt
    ) {
        return new Event(eventId,
                categoryId,
                locationId,
                name,
                content,
                startAt,
                endAt,
                hostId,
                capacity,
                approveType,
                isOnline,
                locationDetail,
                recurringRules,
                deletedAt
        );
    }

    public Event update(UpdateEvent.Command command, RecurringRules recurringRules) {
        // 이미 종료된 이벤트라면 예외 발생
        if(this.endAt().isBefore(LocalDateTime.now())) {
            throw new BaseException(EventException.ALREADY_ENDED_EVENT);
        }

        // 이벤트 업데이트
        return Event.create(
                command.eventId(),
                command.categoryId(),
                command.locationId(),
                command.name(),
                command.content(),
                command.startAt(),
                command.endAt(),
                this.hostId,
                command.capacity(),
                command.approveType(),
                command.isOnline(),
                command.locationDetail(),
                recurringRules,
                this.deletedAt
        );
    }

    // TODO - 이벤트 예약 시, capacity가 --되는 구조? 현재 인원을 받아와서 계산 해야되는지...
    /** 이벤트 예약 승인 */
    public Event decreaseCapacity() {
        return new Event(
                this.eventId,
                this.categoryId,
                this.locationId,
                this.name,
                this.content,
                this.startAt,
                this.endAt,
                this.hostId,
                this.capacity - 1,
                this.approveType,
                this.isOnline,
                this.locationDetail,
                this.recurringRules,
                this.deletedAt
        );
    }

    // TODO - 이벤트 나가기 기능 시, capacity가 ++되는 구조?
    public Event increaseCapacity() {
        // 좌석 최댓값 제한 로직이 필요하면 추가
        return new Event(
                this.eventId,
                this.categoryId,
                this.locationId,
                this.name,
                this.content,
                this.startAt,
                this.endAt,
                this.hostId,
                this.capacity + 1,
                this.approveType,
                this.isOnline,
                this.locationDetail,
                this.recurringRules,
                this.deletedAt
        );
    }
}
