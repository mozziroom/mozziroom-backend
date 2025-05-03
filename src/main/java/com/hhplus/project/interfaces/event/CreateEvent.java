package com.hhplus.project.interfaces.event;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateEvent() {
    public record Request (
            @Schema(description = "이벤트명", example = "서각코 모집")
            String name,

            @Schema(description = "케테고리 Id", example = "2")
            Long categoryId,

            @Schema(description = "장소 Id", example = "지역 id")
            Long location_id,
            @Schema(description = "이벤트 시작 일시", example = "2025-04-10T14:00:00")
            LocalDateTime startAt,
            @Schema(description = "이벤트 종료 일시", example = "2025-04-10T16:00:00")
            LocalDateTime endAt,
            @Schema(description = "정원", example = "30")
            int capacity,

            @Schema(description = "온라인 모임인지 확인 하기 위한 flag",example = "N")
            String is_online,

            @Schema(description = "승인 타입 (AUTO: 자동, MANUAL: 수동)", example = "AUTO")
            EventEnums.ApproveType approveType,
            @Schema(description = "반복 설정 정보")
            RecurringRules recurringRules
    ){}

    public record Response(
            @Schema(description = "이벤트 식별자", example = "101")
            Long eventId
    ){
        public static Response create(){
            return new Response(101L);
        }
    }

    public record RecurringRules(
            @Schema(description = "반복 유형 (YEAR, MON, WEEK, DAY)", example = "WEEK")
            RecurringRulesEnums.Type recurringType,
            @Schema(description = "반복 주기 (몇 주마다 등)", example = "1")
            int recurring_interval,
            @Schema(description = "반복 시작일", example = "2025-04-10")
            LocalDate startAt,
            @Schema(description = "반복 종료일", example = "2025-06-10")
            LocalDate endAt
    ) {}
}
