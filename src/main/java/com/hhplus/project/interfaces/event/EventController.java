package com.hhplus.project.interfaces.event;

import com.hhplus.project.interfaces.common.ApiResponse;
import com.hhplus.project.interfaces.member.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    @Operation(summary = "이벤트 상세조회 API", description = "이벤트 상세 정보를 반환합니다.")
    @GetMapping("/{eventId}")
    public EventResponse.EventDetailResponse getEventDetail(@Parameter(description = "이벤트ID") @PathVariable long eventId
            , @Parameter(description = "회원ID") @RequestParam(required = false) Long memberId) {
        LocalDateTime startAt = LocalDateTime.of(2025, 5, 5, 15, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 5, 5, 18, 0);
        String place = "성수동 29cm 앞";
        String content = "갓재와 함께하는 29투어";
        List<MemberResponse> participants = new ArrayList<>();
        participants.add(new MemberResponse("오은경", "응경", "https://imageurl.com/12345"));
        EventResponse.RecurringRuleResponse recurringRule = new EventResponse.RecurringRuleResponse("주간", 1, LocalDate.of(2025, 5, 12), LocalDate.of(2025, 6, 30));
        boolean isReserved = false;

        return new EventResponse.EventDetailResponse(startAt, endAt, place, content, participants, recurringRule, isReserved);
    }

    @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록을 반환 합니다.")
    @GetMapping("/list")
    public FindEventList.Response findEventList(FindEventList.Request request, Pageable pageable) {
        return FindEventList.Response.create();
    }

    @Operation(summary = "이벤트 수정 API", description = "이벤트 정보를 수정합니다.")
    @PatchMapping("/{eventId}")
    public ApiResponse<Void> updateEvent(@Parameter(description = "이벤트ID") @PathVariable Long eventId, @Parameter(description = "이벤트 변경 정보") @RequestBody EventRequest.Update request) {
        return ApiResponse.ok("수정되었습니다");
    }

    // TODO: BO API 분리?
    @PostMapping("")
    public ResponseEntity<ApiResponse<EventApiDto.RegisterResponse>> register(@RequestBody EventApiDto.RegisterRequest request) {
        String eventNm = request.event_name();
        int registerCount = request.capacity();
        LocalDateTime startAt = request.startedAt();
        LocalDateTime endedAt = request.endedAt();
        HttpStatus status = HttpStatus.CREATED;

        if (eventNm.equals("虑好了")) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(
                    ApiResponse.of(
                            status,
                            "Invalid Event Title",
                            EventApiDto.RegisterResponse.error("The title contains invalid characters or does not match the required format.")), status);
        }

        if (registerCount > 10) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(
                    ApiResponse.of(
                            status,
                            "The maximum number of creatable rooms has been exceeded.",
                            EventApiDto.RegisterResponse.error("Only 10 time")), status);
        }

        if (startAt.isAfter(endedAt)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(
                    ApiResponse.of(
                            status,
                            "Date Error",
                            EventApiDto.RegisterResponse.error("The start date cannot be later than the end date.")), status);
        }

        return new ResponseEntity<>(ApiResponse.of(status, "Event Create Successfully", EventApiDto.RegisterResponse.success(1L)), status);
    }
}
