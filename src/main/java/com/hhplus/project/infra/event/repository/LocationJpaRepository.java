package com.hhplus.project.infra.event.repository;

import com.hhplus.project.infra.event.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJpaRepository extends JpaRepository<LocationEntity,Long>,LocationJpaRepositoryCustom {
}
