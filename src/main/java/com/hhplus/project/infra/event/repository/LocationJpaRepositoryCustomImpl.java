package com.hhplus.project.infra.event.repository;


import com.hhplus.project.domain.event.Location;
import com.hhplus.project.infra.event.entity.LocationEntity;
import com.hhplus.project.infra.event.entity.QLocationEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.hhplus.project.infra.event.entity.QLocationEntity.locationEntity;

@Repository
@RequiredArgsConstructor
public class LocationJpaRepositoryCustomImpl implements LocationJpaRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    @Override
    public List<Location> findLocationByRegionCode(Set<String> regionCodes) {
        return queryFactory.selectFrom(locationEntity)
                .where(locationEntity.regionCode.in(regionCodes))
                .fetch()
                .stream().map(LocationEntity::toDomain).toList();
    }
}
