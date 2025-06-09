package com.hhplus.project.interfaces.event;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateEvent(

) {
    public record Request(
            @NotNull
            @Schema(description = "카테고리 id", example = "123")
            Long categoryId,
            @NotNull
            @Schema(description = "이벤트명", example = "서각코 모집")
            String name,
            @NotNull
            @Schema(description = "스터디 내용", example = "스타벅스에서 모각코 하실 분!")
            String content,
            @NotNull
            @Schema(description = "이벤트 시작 일시", example = "2025-04-10T14:00:00")
            LocalDateTime startAt,
            @NotNull
            @Schema(description = "이벤트 종료 일시", example = "2025-04-10T16:00:00")
            LocalDateTime endAt,
            @NotNull
            @Schema(description = "정원", example = "30")
            int capacity,
            @NotNull
            @Schema(description = "승인 타입 (AUTO: 자동, MANUAL: 수동)", example = "AUTO")
            EventEnums.ApproveType approveType,
            @NotNull
            @Schema(description = "온라인 여부", example = "false")
            boolean isOnline,

            @Schema(description = "지역 id", example = "12")
            Long locationId,
            @NotNull
            @Schema(description = "상세 장소", example = "스타벅스 XX지점")
            String locationDetail,
            @Schema(description = "반복 설정 정보")
            RecurringRules recurringRules
    ) {
        public com.hhplus.project.domain.event.dto.UpdateEvent.Command toCommand(Long eventId, MultipartFile imageFile) {
            com.hhplus.project.domain.event.dto.UpdateEvent.RecurringRules rules = recurringRules == null ?
                    null : recurringRules.toCommand();

            return com.hhplus.project.domain.event.dto.UpdateEvent.Command.create(
                    eventId,
                    categoryId,
                    name,
                    content,
                    startAt,
                    endAt,
                    capacity,
                    approveType,
                    isOnline,
                    locationId,
                    locationDetail,
                    imageFile,
                    rules
                    );
        }
    }

    public record RecurringRules(
            @Schema(description = "반복 규칙 ID", example = "5")
            Long recurringRulesId,
            @Schema(description = "반복 유형 (YEAR, MON, WEEK, DAY)", example = "WEEK")
            RecurringRulesEnums.Type recurringType,
            @Schema(description = "반복 주기 (몇 주마다 등)", example = "1")
            int recurringInterval,
            @Schema(description = "반복 시작일", example = "2025-04-10")
            LocalDate startDate,
            @Schema(description = "반복 종료일", example = "2025-06-10")
            LocalDate endDate
    ) {
        public com.hhplus.project.domain.event.dto.UpdateEvent.RecurringRules toCommand() {
            return com.hhplus.project.domain.event.dto.UpdateEvent.RecurringRules.create(
                    recurringRulesId,
                    recurringType,
                    recurringInterval,
                    startDate,
                    endDate
            );
        }

    }
}
