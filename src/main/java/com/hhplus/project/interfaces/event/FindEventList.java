package com.hhplus.project.interfaces.event;

import com.hhplus.project.domain.event.EventEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record FindEventList(

) {

    public record Request(
            @Schema(description = "검색어", example = "성수 모각코")
            String keyword,
            @Schema(description = "시작일시", example = "2025-04-11T18:40:00")
            LocalDateTime startAt,
            @Schema(description = "종료일시", example = "2025-04-12T18:00:00")
            LocalDateTime endAt,
            // TODO: 정렬 타입같은 Enum 위치 어디에 둘지 정하기
            @Schema(description = "정렬 기준", example = "인기순, 정원순, 최신순 등등.. (POPULAR, MOST_APPLIED, NEW)")
            EventEnums.SortType sortType
    ) {

    }

    public record Response(
            @Schema(description = "이벤트 식별자", example = "101")
            Long eventId,
            @Schema(description = "이벤트명", example = "백엔드 스터디 모집")
            String name,
            @Schema(description = "신청인원", example = "1")
            int currentCapacity,
            @Schema(description = "정원", example = "10")
            int capacity,
            @Schema(description = "장소", example = "성수 29cm 앞")
            String place,
            @Schema(description = "시작일시", example = "2025-04-11T18:30:00")
            LocalDateTime startAt
    ) {

        public static Response create() {
            return new Response(
                    1L,
                    "백엔드 스터디 모집",
                    1,
                    10,
                    "성수 29cm 앞",
                    LocalDateTime.of(2025, 4, 11, 18, 30)
            );
        }
    }
}
