package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.infra.event.entity.RecurringRulesEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Event {
    /** 스터디 id */
    private Long eventId;

    /** 카테고리 id */
    private Long categoryId;

    /** 지역 id */
    private Long locationId;

    /** 스터디명 */
    private String name;

    /** 스터디 내용 */
    private String content;

    /** 스터디 시작시간 */
    private LocalDateTime startAt;

    /** 스터디 종료시간 */
    private LocalDateTime endAt;

    /** 스터디 주최자 (member_id) */
    private Long hostId;

    /** 정원 */
    private int capacity;

    /** 이벤트 승인 타입 */
    private EventEnums.ApproveType approveType;

    /** 온라인 여부 */
    private boolean isOnline;

    /** 상세 장소 */
    private String locationDetail;

    /** 스터디 반복 규칙 */
    private RecurringRulesEntity recurringRules;

    /** 삭제일시 */
    private LocalDateTime deletedAt;

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
            RecurringRulesEntity recurringRules,
            LocalDateTime deletedAt
    ) {
        Event event = new Event();
        event.eventId = eventId;
        event.categoryId = categoryId;
        event.locationId = locationId;
        event.name = name;
        event.content = content;
        event.startAt = startAt;
        event.endAt = endAt;
        event.hostId = hostId;
        event.capacity = capacity;
        event.approveType = approveType;
        event.isOnline = isOnline;
        event.locationDetail = locationDetail;
        event.recurringRules = recurringRules;
        event.deletedAt = deletedAt;
        return event;
    }

    public void updateChangeData(UpdateEvent.Criteria criteria, String imagePath) {
        // 여기서 기간으로 변경 가능 데이터 인지, 아닌지 체크하기

        // 여기서 변경된 데이터를 세팅
    }
}
