package com.hhplus.project.interfaces.event;

import com.hhplus.project.support.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    @Operation(summary = "이벤트 상세조회 API", description = "이벤트 상세 정보를 반환합니다.")
    @GetMapping("/{eventId}")
    public ApiResponse<EventDetail.Response> getEventDetail(@Parameter(description = "이벤트ID") @PathVariable long eventId
            , @Parameter(description = "회원ID") @RequestParam(required = false) Long memberId) {

        return ApiResponse.ok(EventDetail.Response.create());
    }

    @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록을 반환 합니다.")
    @GetMapping("/list")
    public ApiResponse<Page<FindEventList.Response>> findEventList(FindEventList.Request request, Pageable pageable) {
        FindEventList.Response response = FindEventList.Response.create();
        List<FindEventList.Response> responseList = List.of(response);
        return ApiResponse.ok(new PageImpl<>(responseList, pageable, responseList.size()));
    }

    @Operation(summary = "이벤트 수정 API", description = "이벤트 정보를 수정합니다.")
    @PatchMapping("/{eventId}")
    public ApiResponse<Void> updateEvent(@Parameter(description = "이벤트ID") @PathVariable Long eventId, @Parameter(description = "이벤트 변경 정보") @RequestBody UpdateEvent.Request request) {
        return ApiResponse.ok();
    }

    @Operation(summary = "이벤트 생성 API", description = "이벤트를 생성합니다.")
    @PostMapping("")
    public ApiResponse<CreateEvent.Response> createEvent(@Parameter(description = "이벤트 생성 정보") @RequestBody CreateEvent.Request request) {
        return ApiResponse.ok(CreateEvent.Response.create());
    }
}
