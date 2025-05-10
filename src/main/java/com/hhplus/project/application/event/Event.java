package com.hhplus.project.application.event;

import com.hhplus.project.domain.event.CategoryService;
import com.hhplus.project.domain.event.CreateEventService;
import com.hhplus.project.domain.event.EventImageService;
import com.hhplus.project.domain.event.LocationService;
import com.hhplus.project.domain.member.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class Event {
    private final CreateEventService createEventService;
    private final MemberService memberService;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final EventImageService eventImageService;

    @Transactional
    public CreateEvent.Result create(CreateEvent.Criteria criteria){
        // find User for Creatable Event
        Long memberId   = memberService.findEventMaker(criteria.Token());

        // find Location
        Long locationId = locationService.find(criteria.locationId());

        // find Category
        Long categoryId = categoryService.find(criteria.categoryId());

        // image valid check
        eventImageService.checkImage(criteria.thumbNail());

        //

        return null;
    }
}
