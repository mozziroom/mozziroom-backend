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
            // TODO 고민! 회원이 이벤트를 조회하면 참석여부에 따라 '참석' 또는 '참석취소' 버튼이 보여야 한다고 생각하는데 이것을 이 API 에서 내리는 게 맞는지 고민...
            @Schema(description = "참석신청여부", example = "true")
            boolean isReserved
    ) {
        public static EventDetail.Response create() {
            List<MemberResponse> participants = new ArrayList<>();
            participants.add(new MemberResponse("오은경", "응경", "https://imageurl.com/12345"));

            return new EventDetail.Response(
                    LocalDateTime.of(2025, 5, 5, 15, 0),
                    LocalDateTime.of(2025, 5, 5, 18, 0),
                    "성수동 29cm 앞",
                    "갓재와 함께하는 29투어",
                    participants,
                    EventDetail.RecurringRule.create(),
                    false
            );
        }
    }

    @Schema(description = "반복규칙 Response")
    public record RecurringRule(
            @Schema(description = "반복 타입", example = "WEEKLY")
            String recurringType,
            @Schema(description = "주기", example = "1")
            int recurring_interval,
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
}
