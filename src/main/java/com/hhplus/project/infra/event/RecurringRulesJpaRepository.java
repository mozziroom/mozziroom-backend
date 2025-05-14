package com.hhplus.project.infra.event;

import com.hhplus.project.infra.event.entity.RecurringRulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurringRulesJpaRepository extends JpaRepository<RecurringRulesEntity,Long> {
}
