package com.hhplus.project.domain.event;

import com.hhplus.project.domain.event.dto.CreateCategories;
import com.hhplus.project.domain.event.dto.CreateEvent;
import com.hhplus.project.domain.event.dto.CreateLocations;
import com.hhplus.project.domain.event.dto.UpdateEvent;
import com.hhplus.project.support.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event getEvent(long eventId) {
        return eventRepository.getEvent(eventId);
    }

    public Page<Event> findEventList(EventList.Command command) {
        return eventRepository.findEventList(command);
    }

    @Transactional
    public Event update(UpdateEvent.Command command) {
        // 이벤트 조회
        Event event = eventRepository.getEvent(command.eventId());
        // 이벤트 도메인 모델 정보 변경
        Event updatedEvent = event.update(command, null);

        return eventRepository.save(updatedEvent);
    }

    public CreateEvent.Info create(CreateEvent.Command command) {
        if (eventRepository.findCategory(command.categoryId()).isEmpty()) {
            throw new BaseException(EventException.CATEGORY_NOT_FOUND);
        }

        if (eventRepository.findLocation(command.locationId()).isEmpty()) {
            throw new BaseException(EventException.LOCATION_NOT_FOUND);
        }
        return CreateEvent.Info.fromDomain(eventRepository.save(command.toDomain()));
    }


    @Transactional
    public CreateLocations.Info createLocationList(CreateLocations.Command command){
        Set<String> uniqueRegionCodes = command.locationList().stream()
                .map(CreateLocations.LocationInfo::regionCode)
                .collect(Collectors.toSet());

        if( !eventRepository.findLocation(uniqueRegionCodes).isEmpty() ){
            throw new BaseException(EventException.ALREADY_EXIST_REGION_CODE);
        }

        return new CreateLocations.Info(
                eventRepository.saveLocations(command.toDomain())
                        .stream()
                        .map(Location::locationId)
                        .toList());
    }

    @Transactional
    public CreateCategories.Info createCategoryList(CreateCategories.Command command){
        Set<Long> uniqueParentCategoryList = command.categoryInfoList().stream()
                .map(CreateCategories.CategoryInfo::parentId)
                .collect(Collectors.toSet());

        Map<Long, Boolean> parentCategoryMap = new HashMap<>();
        eventRepository.findCategory(uniqueParentCategoryList)
                .forEach(category -> parentCategoryMap.put(category.categoryId(), category.isActive()));

        List<Category> categories = command.categoryInfoList().stream()
                .map(info -> {
                    if (info.parentId() == null) {
                        return info.toDomain();
                    } else {
                        if (!parentCategoryMap.containsKey(info.parentId())) {
                            throw new BaseException(EventException.EVENT_NOT_FOUND);
                        }
                        if (!parentCategoryMap.get(info.parentId())) {
                            throw new BaseException(EventException.INVALID_PARENT_CATEGORY);
                        }
                        return info.toDomain();
                    }
                })
                .toList();

        return new CreateCategories.Info(eventRepository.saveCategories(categories).stream()
                .map(Category::categoryId)
                .toList());
    }

}
