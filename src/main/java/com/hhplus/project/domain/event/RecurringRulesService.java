package com.hhplus.project.domain.event;

public interface RecurringRulesService {
    CreateRecurringRules.Domain create(CreateRecurringRules.Command command);
}
