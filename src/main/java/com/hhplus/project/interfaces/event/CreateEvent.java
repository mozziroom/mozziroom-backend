package com.hhplus.project.interfaces.event;

import com.hhplus.project.application.event.dto.CreateEventFacade;
import com.hhplus.project.domain.event.EventEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

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
            @Schema(description = "이벤트 일", example = "2025-04-10")
            LocalDate eventDate,

            @NotNull
            @Schema(description = "이벤트 시작 시간", example = "14:00:00")
            LocalTime endTime,

            @NotNull
            @Schema(description = "이벤트 종료 시간", example = "16:00:00")
            LocalTime startTime,

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
            String locationDetail
    )
    {
        public CreateEventFacade.Criteria toCriteria(String token){
            return new CreateEventFacade.Criteria(
                    categoryId,
                    locationId,
                    name,
                    content,
                    eventDate,
                    startTime,
                    endTime,
                    token,
                    capacity,
                    approveType,
                    isOnline,
                    locationDetail
            );
        }
    }

    public record Response(
            @Schema(description = "이벤트 식별자", example = "101")
            Long eventId
    ){
        public static Response fromResult(CreateEventFacade.Result result){
            return new Response(result.eventId());
        }
    }
}
