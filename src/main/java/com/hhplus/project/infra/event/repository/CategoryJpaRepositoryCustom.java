package com.hhplus.project.infra.event.repository;

import com.hhplus.project.domain.event.Category;

import java.util.List;
import java.util.Set;

public interface CategoryJpaRepositoryCustom {

    List<Category> findCategoryList(Set<Long> categoryIdList);
}
