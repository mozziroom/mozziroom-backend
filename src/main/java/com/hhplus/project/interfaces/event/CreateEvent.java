package com.hhplus.project.interfaces.event;

import com.hhplus.project.domain.event.EventEnums;
import com.hhplus.project.domain.event.RecurringRulesEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CreateEvent() {
    public record Request (

            @NotNull
            @Schema(description = "케테고리 Id", example = "2")
            Long categoryId,

            @NotNull
            @Schema(description = "장소 Id", example = "지역 id")
            Long locationId,

            @NotNull
            @Schema(description = "이벤트명", example = "서각코 모집")
            String name,

            @NotNull
            @Schema(description = "이벤트 내용", example = "서각코 모집 ㅎㅎ")
            String content,

            @NotNull
            @Schema(description = "이벤트 시작 일시", example = "2025-04-10T14:00:00")
            LocalDateTime startAt,

            @NotNull
            @Schema(description = "이벤트 종료 일시", example = "2025-04-10T16:00:00")
            LocalDateTime endAt,

            @NotNull
            @Schema(description = "이벤트 주인 Id", example = "1L")
            Long hostId,

            @NotNull
            @Schema(description = "정원", example = "30")
            int capacity,

            @NotNull
            @Schema(description = "승인 타입 (AUTO: 자동, MANUAL: 수동)", example = "AUTO")
            EventEnums.ApproveType approveType,

            @NotNull
            @Schema(description = "온라인 모임인지 확인 하기 위한 flag",example = "N")
            Boolean isOnline,

            @NotNull
            @Schema(description = "주소 디테일")
            String locationDetail,

            @Valid
            @Schema(description = "반복 설정 정보")
            RecurringRules recurringRules

    ){
        public com.hhplus.project.application.event.CreateEvent.Criteria toCriteria(MultipartFile file){
            return new com.hhplus.project.application.event.CreateEvent.Criteria(
                    categoryId(),
                    locationId(),
                    name(),
                    content(),
                    startAt(),
                    endAt(),
                    hostId(),
                    capacity(),
                    approveType(),
                    isOnline(),
                    locationDetail(),
                    recurringRules.toCriteria(),
                    file
            );
        }
    }

    public record Response(
            @Schema(description = "이벤트 식별자", example = "101")
            List<Long> eventIds
    ){

        public static Response fromResult(com.hhplus.project.application.event.CreateEvent.Result result){
            return new Response(result.eventIds());
        }
    }

    public record RecurringRules(
            @NotNull
            @Schema(description = "반복 유형 (YEAR, MON, WEEK, DAY)", example = "WEEK")
            RecurringRulesEnums.Type recurringType,
            @NotNull
            @Schema(description = "반복 주기 (몇 주마다 등)", example = "1")
            int recurringInterval,
            @NotNull
            @Schema(description = "반복 시작일", example = "2025-04-10")
            LocalDate startAt,
            @NotNull
            @Schema(description = "반복 종료일", example = "2025-06-10")
            LocalDate endAt
    ) {

        public com.hhplus.project.application.event.CreateEvent.RecurringRules toCriteria(){
            return new com.hhplus.project.application.event.CreateEvent.RecurringRules(
                    recurringType(),
                    recurringInterval(),
                    startAt(),
                    endAt()
            );
        }
    }
}
