package com.hhplus.project.interfaces.event;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateEvent(

) {
    public record Request(
            @Schema(description = "이벤트명", example = "서각코 모집")
            String name,
            @Schema(description = "이벤트 시작 일시", example = "2025-04-10T14:00:00")
            LocalDateTime startAt,
            @Schema(description = "이벤트 종료 일시", example = "2025-04-10T16:00:00")
            LocalDateTime endAt,
            @Schema(description = "정원", example = "30")
            int capacity,
            @Schema(description = "장소", example = "서울 강남구 스타벅스")
            String place,
            @Schema(description = "승인 타입 (AUTO: 자동, MANUAL: 수동)", example = "AUTO")
            EventEnums.ApproveType approveType,
            @Schema(description = "반복 설정 정보")
            RecurringRules recurringRules
    ) {}

    public record RecurringRules(
            @Schema(description = "반복 규칙 ID", example = "5")
            Long recurringRulesId,
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
