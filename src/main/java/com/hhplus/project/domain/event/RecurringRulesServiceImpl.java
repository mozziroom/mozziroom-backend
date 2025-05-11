package com.hhplus.project.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecurringRulesServiceImpl implements RecurringRulesService{

    @Override
    public CreateRecurringRules.Domain create(CreateRecurringRules.Command command) {
        // SAVE RECURRING RULE

        // GET RECURRING DATES

        return null;
    }
}
