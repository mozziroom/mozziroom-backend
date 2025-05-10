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
            @Schema(description = "지역 Id", example = "1")
            Long locationId,
            @Schema(description = "최소 정원", example = "1")
            Integer minCapacity,
            @Schema(description = "최대 정원", example = "5")
            Integer maxCapacity,
            @Schema(description = "카테고리 Id", example = "1")
            String categoryId,
            @Schema(description = "정렬 기준", example = "인기순, 정원순, 최신순 등등.. (POPULAR, MOST_APPLIED, NEW)")
            EventEnums.SortType sortType
    ) {

    }

    public record Response(
            @Schema(description = "이벤트 Id", example = "101")
            Long eventId,
            @Schema(description = "이벤트 썸네일 이미지 경로")
            String thumbnailImagePath,
            @Schema(description = "이벤트명", example = "백엔드 스터디 모집")
            String name,
            @Schema(description = "신청인원", example = "1")
            int currentCapacity,
            @Schema(description = "정원", example = "10")
            int capacity,
            @Schema(description = "장소")
            Location location,
            @Schema(description = "카테고리")
            Category category,
            @Schema(description = "시작일시", example = "2025-04-11T18:30:00")
            LocalDateTime startAt,
            @Schema(description = "종료일시", example = "2025-04-22T18:30:00")
            LocalDateTime endAt
    ) {

        public static Response create() {
            return new Response(
                    1L,
                    "https://aws-djfalkdjfeipfj-dkfjaldj/event/images/123954.jpg",
                    "백엔드 스터디 모집",
                    1,
                    10,
                    Location.create(),
                    Category.create(),
                    LocalDateTime.of(2025, 4, 11, 18, 30),
                    LocalDateTime.of(2025, 4, 22, 18, 30)
            );
        }

        @Schema(description = "장소")
        public record Location(
                @Schema(description = "시/도", example = "서울시")
                String city,
                @Schema(description = "구", example = "성동구")
                String district,
                @Schema(description = "동", example = "성수동")
                String neighborhood,
                @Schema(description = "상세 주소", example = "29cm 앞")
                String locationDetail
        ) {
            public static Location create() {
                return new Location(
                        "서울시", "성동구", "성수동", "29cm 앞");
            }
        }

        @Schema(description = "카테고리")
        public record Category(
                @Schema(description = "카테고리 명", example = "스터디 > 개발 > 백엔드")
                String fullName
        ) {
            public static Category create() {
                return new Category("스터디 > 개발 > 백엔드");
            }
        }
    }
}
