package com.hhplus.project.domain.event;


import com.hhplus.project.support.BaseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.hhplus.project.domain.event.RecurringRulesEnums.Type.*;

public record CreateRecurringRules() {
    public record Command(
            RecurringRulesEnums.Type type,
            Integer interval,
            LocalDate startDate,
            LocalDate endDate
    ){

        public static Command create(
                String recurringType,
                Integer interval,
                LocalDate startDate,
                LocalDate endDate
        ){
            if( endDate.isBefore(startDate) ){
                throw new BaseException(EventException.WRONG_TIME_SETTING);
            }

            try{
                RecurringRulesEnums.Type ruleType = RecurringRulesEnums.Type.valueOf(recurringType.toUpperCase());
                return new Command(ruleType, interval, startDate, endDate);
            }
            catch (Exception e){
                throw new BaseException(EventException.RECURRING_TYPE);
            }
        }
    }
    public record Domain( RecurringRules  recurringRules, List<LocalDate> eventDates){
        public static List<LocalDate> createEventDate(
                RecurringRulesEnums.Type recurringType,
                Integer recurringInterval,
                LocalDate startDate,
                LocalDate endDate
        ){
            List<LocalDate> result = new ArrayList<>();
            LocalDate current = startDate;

            while (!current.isAfter(endDate)) {
                result.add(current);

                switch (recurringType) {
                    case DAY -> current = current.plusDays(recurringInterval);
                    case WEEK -> current = current.plusWeeks(recurringInterval);
                    case MON  -> current = current.plusMonths(recurringInterval);
                    case YEAR -> current = current.plusYears(recurringInterval);
                }
            }

            return result;
        }
    }
}
