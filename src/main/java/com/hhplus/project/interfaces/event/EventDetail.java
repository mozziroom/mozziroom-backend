package com.hhplus.project.interfaces.event;

import com.hhplus.project.interfaces.member.MemberResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDetail {

    public record Request(

    ) {
    }

    @Schema(description = "이벤트 상세조회 Response")
    public record Response(
            @Schema(description = "이벤트 시작일시", example = "2025-05-05 15:00:00")
            LocalDateTime startAt,
            @Schema(description = "이벤트 종료일시", example = "2025-05-05 18:00:00")
            LocalDateTime endAt,
            @Schema(description = "이벤트 장소", example = "성수동 29cm")
            String place,
            @Schema(description = "이벤트 내용", example = "갓재와 함께하는 29투어")
            String content,
            @Schema(description = "참석자 목록")
            List<MemberResponse> participants,
            @Schema(description = "반복규칙")
            RecurringRule recurringRule,
            @Schema(description = "카테고리")
            Category category,
            // TODO 장소의 존재여부에 따라 온라인여부 체크하는 것은 별로인가?
            @Schema(description = "온라인 여부")
            String isOnline,
            @Schema(description = "장소")
            Location location,
            @Schema(description = "이벤트 이미지 목록")
            List<EventImage> eventImages,
            @Schema(description = "호스트 여부")
            String isHost,
            @Schema(description = "예약상태", example = "PENDING")
            String reservationStatus
    ) {
        public static EventDetail.Response create() {
            List<MemberResponse> participants = new ArrayList<>();
            participants.add(new MemberResponse("오은경", "응경", "https://imageurl.com/12345"));

            List<EventImage> eventImages = new ArrayList<>();
            eventImages.add(new EventImage(1L, "https://aws-djfalkdjfeipfj-dkfjaldj/event/images/123954.jpg", 1, "MAIN"));
            eventImages.add(new EventImage(2L, "https://aws-djfalkdjfeipfj-dkfjaldj/event/images/123955.jpg", 2, "OTHER"));

            return new EventDetail.Response(
                    LocalDateTime.of(2025, 5, 5, 15, 0),
                    LocalDateTime.of(2025, 5, 5, 18, 0),
                    "성수동 29cm 앞",
                    "갓재와 함께하는 29투어",
                    participants,
                    EventDetail.RecurringRule.create(),
                    Category.create(),
                    "N",
                    Location.create(),
                    eventImages,
                    "N",
                    "PENDING"
            );
        }
    }

    @Schema(description = "반복규칙 Response")
    public record RecurringRule(
            @Schema(description = "반복 타입", example = "WEEKLY")
            String recurringType,
            @Schema(description = "주기", example = "1")
            int recurringInterval,
            @Schema(description = "반복 시작일자", example = "2025-06-01")
            LocalDate startDate,
            @Schema(description = "반복 종료일자", example = "2025-12-31")
            LocalDate endDate
    ) {
        public static RecurringRule create() {
            return new EventDetail.RecurringRule(
                    "주간", 1, LocalDate.of(2025, 5, 12), LocalDate.of(2025, 6, 30));
        }
    }

    @Schema(description = "카테고리 Response")
    public record Category (
            @Schema(description = "카테고리명", example = "스터디")
            String name
    ) {
        public static Category create() {
            return new EventDetail.Category("투어");
        }
    }

    @Schema(description = "장소 Response")
    public record Location (
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
            return new EventDetail.Location(
                    "서울시", "성동구", "성수동", "29cm 앞");
        }
    }

    public record EventImage (
            @Schema(description = "이벤트 이미지 ID")
            Long eventImageId,
            @Schema(description = "이벤트 이미지 경로")
            String eventImagePath,
            @Schema(description = "정렬 순서")
            int sort,
            @Schema(description = "이미지 타입")
            String imageType
    ) {}
}
