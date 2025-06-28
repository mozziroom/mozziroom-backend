package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.Location;

import java.util.List;
import java.util.Set;

public interface LocationJpaRepositoryCustom {
    List<Location> findLocationByRegionCode(Set<String> regionCodes);
}
