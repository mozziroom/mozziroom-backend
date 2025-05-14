package com.hhplus.project.infra.event;

import com.hhplus.project.domain.event.RecurringRules;
import com.hhplus.project.domain.event.RecurringRulesRepository;
import com.hhplus.project.infra.event.entity.RecurringRulesEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecurringRulesRepositoryImpl implements RecurringRulesRepository {
    private final RecurringRulesJpaRepository jpaRepository;
    @Override
    public RecurringRules create(RecurringRules recurringRules) {
        return jpaRepository.save(RecurringRulesEntity.fromDomain(recurringRules)).toDomain();
    }
}
