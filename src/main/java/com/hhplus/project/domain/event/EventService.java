package com.hhplus.project.domain.event;

import com.hhplus.project.support.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    // Repository 묶기
    private final EventRepository eventRepository;
    private final RecurringRulesRepository recurringRulesRepository;

    @Transactional
    public CreateEvent.Info create(CreateEvent.Command command) {

        // get categoryId
        if( eventRepository.findCategory(command.categoryId()).isEmpty() ){
            throw new BaseException(EventException.CATEGORY_NOT_FOUND);
        }

        // find Location
        if ( eventRepository.findLocation(command.locationId()).isEmpty() ){
            throw new BaseException(EventException.LOCATION_NOT_FOUND);
        }

        // Create Recurring Rules
        RecurringRules recurringRules = recurringRulesRepository.create(command.recurringRules());

        // Create Domain By Recurring Rules -> CreateEvents
        // Recurring rules 없을때 추가
        List<Event> createdEvents     = eventRepository.create(command.toDomain(recurringRules).createEvents());


        // Event 자체를 return 하고 facade 에서 이미지랑 함께 처리
        return CreateEvent.Info.fromDomain(createdEvents);
    }

}

