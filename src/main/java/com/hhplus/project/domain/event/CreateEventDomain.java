package com.hhplus.project.domain.event;


import com.hhplus.project.support.BaseException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

/**
 * Event 를 생성하려면
 * - categoryId
 * - locationId
 * - name : 유효성 검증하기
 * - content : 30 글자 이상 되는지 확인
 */
@Getter
public class CreateEventDomain {

    private static final Pattern TITLE_REGEX = Pattern.compile(
            "^[가-힣A-Za-z0-9_\\-\\(\\)!?,\\[\\]@#\\$%\\^&\\*\\uD83C-\\uDBFF\\uDC00-\\uDFFF]{2,}$"
    );

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Content is required")
    @Size(min = 30, message = "Content must be at least 30 characters long")
    private String content;

    /** 스터디 시작시간 */
    @NotNull(message = "Start time is required")
    private LocalDateTime startAt;

    /** 스터디 종료시간 */
    @NotNull(message = "End time is required")
    private LocalDateTime endAt;

    /** 스터디 주최자 (member_id) */
    @NotNull(message = "Host ID is required")
    private Long hostId;

    /** 정원 */
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    /** 이벤트 승인 타입 */
    @NotNull(message = "Approve type is required")
    private Boolean isApprove;

    /** 온라인 여부 */
    @NotNull(message = "Online status is required")
    private Boolean isOnline;

    /** 상세 장소 */
    @NotBlank(message = "Location detail is required")
    private String locationDetail;

    /** 스터디 반복 규칙 ID */
    private RecurringRules rule;

    public CreateEventDomain(
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Long hostId,
            Integer capacity,
            Boolean isApprove,
            Boolean isOnline,
            String locationDetail,
            RecurringRules rule
    ) {
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.name = name;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.hostId = hostId;
        this.capacity = capacity;
        this.isApprove = isApprove;
        this.isOnline = isOnline;
        this.locationDetail = locationDetail;
        this.rule = rule;
    }

    public static CreateEventDomain create(
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Long hostId,
            Integer capacity,
            Boolean isApprove,
            Boolean isOnline,
            String locationDetail,
            RecurringRules rule
    ){
        if( !TITLE_REGEX.matcher(name).matches() ){
            throw new BaseException(EventException.TITLE_REGEX);
        }

        if ( ChronoUnit.HOURS.between(startAt,endAt) < 1 ) {
            throw new BaseException(EventException.WRONG_TIME_SETTING);
        }

        return new CreateEventDomain(
                categoryId,
                locationId,
                name,
                content,
                startAt,
                endAt,
                hostId,
                capacity,
                isApprove,
                isOnline,
                locationDetail,
                rule
        );
    }

}
