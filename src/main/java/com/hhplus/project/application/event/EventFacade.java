package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.*;
import com.hhplus.project.domain.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventFacade {
    private final CreateEventService createEventService;
    private final MemberService memberService;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final EventImageService eventImageService;
    private final RecurringRulesService recurringRulesService;

    @Transactional
    public CreateEvent.Result create(CreateEvent.Criteria criteria){
        // find User for Creatable Event
        Long memberId   = memberService.findEventMaker(criteria.Token());

        // find Location
        locationService.find(criteria.locationId());

        // find Category
        categoryService.find(criteria.categoryId());

        // image valid check
        if( !criteria.thumbNail().isEmpty() ){
            eventImageService.checkImage(criteria.thumbNail());
        }

        // RecurringRules
        List<Long> createEventIds = new ArrayList<>();

        if(criteria.ruleExist()){
            CreateRecurringRules.Domain rulesDomain = recurringRulesService.create(criteria.createRuleCommand());
            createEventService.createEvent(
                    criteria.createEventCommand(memberId,rulesDomain)).forEach(
                            domain -> createEventIds.add(domain.eventId())
            );
        }
        else{
            Long createdEventId = createEventService.createEvent(criteria.createEventCommand(memberId)).eventId();
            createEventIds.add(createdEventId);
        }

        return new CreateEvent.Result(createEventIds);

    }
}
