package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.*;
import com.hhplus.project.infra.event.entity.CategoryEntity;
import com.hhplus.project.infra.event.entity.EventEntity;
import com.hhplus.project.infra.event.entity.LocationEntity;
import com.hhplus.project.support.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    
    @Override
    public Event getEvent(Long eventId) {
        return eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(EventException.EVENT_NOT_FOUND)).toDomain();
    }

    @Override
    public Event findEventWithLock(Long eventId) {
        return eventJpaRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new BaseException(EventException.EVENT_NOT_FOUND)).toDomain();
    }

    @Override
    public Optional<Location> findLocation(Long locationId) {
        return locationJpaRepository.findById(locationId).map(LocationEntity::toDomain);
    }

    @Override
    public Optional<Category> findCategory(Long categoryId) {
        return categoryJpaRepository.findById(categoryId).map(CategoryEntity::toDomain);
    }

    @Override
    public Page<Event> findEventList(EventList.Command command) {
        return eventJpaRepository.findEventList(command);
    }

    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(EventEntity.fromDomain(event, null)).toDomain();
    }

    @Override
    public Category save(Category category) {
        return categoryJpaRepository.save(CategoryEntity.fromDomain(category)).toDomain();
    }

    @Override
    public Location save(Location location) {
        return locationJpaRepository.save(LocationEntity.fromDomain(location)).toDomain();
    }

    @Override
    public List<Location> saveLocations(List<Location> locationList) {
        return locationJpaRepository
                .saveAll(locationList.stream().map(LocationEntity::fromDomain).toList())
                .stream()
                .map(LocationEntity::toDomain).toList();
    }

    @Override
    public List<Category> saveCategories(List<Category> categoryList) {
        return categoryJpaRepository
                .saveAll(categoryList.stream().map(CategoryEntity::fromDomain).toList())
                .stream()
                .map(CategoryEntity::toDomain).toList();
    }

    @Override
    public List<Location> findLocation(Set<String> regionCode) {
        return locationJpaRepository.findLocationByRegionCode(regionCode);
    }

    @Override
    public List<Category> findCategory(Set<Long> categoryIdList) {
        return categoryJpaRepository.findCategoryList(categoryIdList);
    }
}
