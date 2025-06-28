package com.hhplus.project.infra.event.repository;


import com.hhplus.project.domain.event.Category;
import com.hhplus.project.infra.event.entity.CategoryEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.hhplus.project.infra.event.entity.QCategoryEntity.categoryEntity;

@Repository
@RequiredArgsConstructor
public class CategoryJpaRepositoryCustomImpl implements CategoryJpaRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Category> findCategoryList(Set<Long> categoryIdList) {
        return jpaQueryFactory.selectFrom(categoryEntity)
                .where(categoryEntity.categoryId.in(categoryIdList))
                .fetch()
                .stream().map(CategoryEntity::toDomain)
                .toList();
    }
}
