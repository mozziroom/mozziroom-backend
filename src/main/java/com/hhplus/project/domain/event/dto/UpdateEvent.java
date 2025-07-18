package com.hhplus.project.domain.event.dto;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateEvent() {
    public record Command(
            @Schema(description = "이벤트 id", example = "1")
            Long eventId,
            @Schema(description = "카테고리 id", example = "123")
            Long categoryId,
            @Schema(description = "이벤트명", example = "서각코 모집")
            String name,
            @Schema(description = "스터디 내용", example = "스타벅스에서 모각코 하실 분!")
            String content,
            @Schema(description = "이벤트 시작 일시", example = "2025-04-10T14:00:00")
            LocalDateTime startAt,
            @Schema(description = "이벤트 종료 일시", example = "2025-04-10T16:00:00")
            LocalDateTime endAt,
            @Schema(description = "정원", example = "30")
            int capacity,
            @Schema(description = "승인 타입 (AUTO: 자동, MANUAL: 수동)", example = "AUTO")
            EventEnums.ApproveType approveType,
            @Schema(description = "온라인 여부", example = "false")
            boolean isOnline,
            @Schema(description = "지역 id", example = "12")
            Long locationId,
            @Schema(description = "상세 장소", example = "스타벅스 XX지점")
            String locationDetail,
            @Schema(description = "이벤트 이미지", example = "파일 업로드 예: event.jpg")
            MultipartFile imageFile,
            @Schema(description = "반복 설정 정보")
            RecurringRules recurringRules
    ) {
        public static Command create(
                Long eventId,
                Long categoryId,
                String name,
                String content,
                LocalDateTime startAt,
                LocalDateTime endAt,
                int capacity,
                EventEnums.ApproveType approveType,
                boolean isOnline,
                Long locationId,
                String locationDetail,
                MultipartFile imageFile,
                RecurringRules recurringRules
        ) {
            return new Command(
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
                    recurringRules
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
        public static RecurringRules create(
                Long recurringRulesId,
                RecurringRulesEnums.Type recurringType,
                int recurringInterval,
                LocalDate startDate,
                LocalDate endDate
        ) {
            return new RecurringRules(
                    recurringRulesId,
                    recurringType,
                    recurringInterval,
                    startDate,
                    endDate
            );
        }
    }
}
