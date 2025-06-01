package com.hhplus.project.infra.event.repository;

import com.hhplus.project.infra.event.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity,Long>,CategoryJpaRepositoryCustom {
}
