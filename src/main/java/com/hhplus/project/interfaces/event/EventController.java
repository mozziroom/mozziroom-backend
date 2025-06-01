package com.hhplus.project.interfaces.event;

import com.hhplus.project.application.event.EventDetailResult;
import com.hhplus.project.domain.event.EventService;
import com.hhplus.project.application.event.EventFacade;
import com.hhplus.project.application.event.EventResult;
import com.hhplus.project.support.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventFacade eventFacade;

    @Operation(summary = "이벤트 상세조회 API", description = "이벤트 상세 정보를 반환합니다.")
    @GetMapping("/{eventId}")
    public ApiResponse<EventDetail.Response> getEventDetail(@Parameter(description = "이벤트ID") @PathVariable long eventId
            , @Parameter(description = "회원ID") @RequestParam(required = false) Long memberId) {
        EventDetailResult.EventDetail event = eventFacade.getEvent(eventId, memberId);
        return ApiResponse.ok(EventDetail.Response.create());
    }

    @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록을 반환 합니다.")
    @GetMapping("/list")
    public ApiResponse<Page<FindEventList.Response>> findEventList(FindEventList.Request request, Pageable pageable) {
        Page<EventResult.Events> events = eventFacade.findEventList(request.toCriteria(pageable));
        return ApiResponse.ok(events.map(FindEventList.Response::from));
    }

    @Operation(summary = "이벤트 수정 API", description = "이벤트 정보를 수정합니다.")
    @PatchMapping(path = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> updateEvent(
            @Parameter(description = "이벤트ID") @PathVariable Long eventId
            , @Parameter(description = "이벤트 변경 정보") @RequestPart UpdateEvent.Request request
            , @Parameter(description = "이벤트 썸네일 파일") @RequestPart(value = "image",required = false) MultipartFile imageFile) {
        // eventService.update(request.toCommand(eventId, imageFile));
        return ApiResponse.ok();
    }

    @Operation(summary = "이벤트 생성 API", description = "이벤트를 생성합니다.")
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CreateEvent.Response> createEvent(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "이벤트 생성 정보")    @RequestPart CreateEvent.Request request,
            @Parameter(description = "이벤트 썸네일 파일")  @RequestPart(value = "image",required = false) MultipartFile imageFile) {
        return ApiResponse.ok(CreateEvent.Response.fromResult(eventFacade.createEvent(request.toCriteria(token))));
    }
}
