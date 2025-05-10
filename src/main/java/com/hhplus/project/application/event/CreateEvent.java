package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.CreateRecurringRules;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record CreateEvent() {

    public record Criteria(
            // USER
            String Token,

            // EVENT
            Long categoryId,
            Long locationId,
            String name,
            String content,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Integer capacity,
            String approveType,
            Boolean isOnline,
            String locationDetail,

            // RECURRING RULE
            Boolean ruleExist,
            String recurringType,
            Integer recurringInterval,
            LocalDate startDate,
            LocalDate endDate,


            // FILE
            MultipartFile thumbNail
    ){

        public CreateRecurringRules.Command createRuleCommand(){
            return CreateRecurringRules.Command.create(recurringType(),
                    recurringInterval(),
                    startDate(),
                    endDate());
        }

        public List<com.hhplus.project.domain.event.CreateEvent.Command> createEventCommand(
                Long memberId,
                CreateRecurringRules.Domain ruleDomain
        ){

            LocalTime startTime = startAt().toLocalTime();
            LocalTime endTime   = endAt().toLocalTime();

            return ruleDomain.eventDates().stream().map(eventDate->
                com.hhplus.project.domain.event.CreateEvent.Command.create(
                        categoryId(),
                        locationId(),
                        name(),
                        content(),
                        LocalDateTime.of(eventDate,startTime),
                        LocalDateTime.of(eventDate,endTime),
                        memberId,
                        capacity(),
                        approveType(),
                        isOnline(),
                        locationDetail(),
                        ruleDomain.recurringRules()
                )).toList();
        }

        public com.hhplus.project.domain.event.CreateEvent.Command createEventCommand(Long memberId){
            return com.hhplus.project.domain.event.CreateEvent.Command.create(
                    categoryId(),
                    locationId(),
                    name(),
                    content(),
                    startAt(),
                    endAt(),
                    memberId,
                    capacity(),
                    approveType(),
                    isOnline(),
                    locationDetail(),
                    null
            );
        }
    }

    public record Result(List<Long> eventIds){}
}
