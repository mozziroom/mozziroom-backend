package com.hhplus.project.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final RecurringRulesRepository recurringRulesRepository;
    private final EventImageRepository imageRepository;

    public CreateEvent.Domain create(CreateEvent.Command command){

        // find categoryId
        getCategory(command.categoryId());

        // find commandId
        getLocation(command.locationId());

        // Create Recurring Rules
        RecurringRules recurringRules = recurringRulesRepository.create(command.recurringRules());

        // Create Domain By Recurring Rules -> CreateEvents
        List<Event> createdEvents     = eventRepository.create(createEvents(command,recurringRules));

        // Create Event Image ( Thumbnail )
        createEventImage(createdEvents, command.file());

        return CreateEvent.Domain.fromDomain(createdEvents);
    }

    private void createEventImage(List<Event> createEvents, MultipartFile file) {
        if (!file.isEmpty() && isImage(file)) {
            String filePath = imageRepository.saveFile(file);
            imageRepository.saveAll(
                    createEvents.stream()
                            .map(event -> new EventImage(
                                    null,
                                    event,
                                    EventEnums.ImageType.THUMBNAIL,
                                    file.getOriginalFilename(),
                                    filePath,
                                    0
                            )).toList());
        }
    }
    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.startsWith("image/");
    }
    private List<Event>     createEvents(CreateEvent.Command command, RecurringRules recurringRules){
        return calculateDates(command.recurringRules()).stream()
                .map(date -> {
                    LocalDateTime startAt = LocalDateTime.of(date,command.startAt().toLocalTime());
                    LocalDateTime endAt   = LocalDateTime.of(date,command.endAt().toLocalTime());
                    return command.toDomain(recurringRules,startAt,endAt);
                }).toList();
    }
    private List<LocalDate> calculateDates(RecurringRules rules){
        List<LocalDate> result = new ArrayList<>();
        LocalDate current = rules.startDate();

        while (!current.isAfter(rules.endDate())) {
            result.add(current);
            switch (rules.recurringType()) {
                case DAY -> current = current.plusDays(rules.recurringInterval());
                case WEEK -> current = current.plusWeeks(rules.recurringInterval());
                case MON  -> current = current.plusMonths(rules.recurringInterval());
                case YEAR -> current = current.plusYears(rules.recurringInterval());
            }
        }
        return result;
    }
    private void getCategory(Long categoryId){
    }
    private void getLocation(Long locationId){
    }
}

