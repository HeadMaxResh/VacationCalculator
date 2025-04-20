package org.example.utils;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.HashSet;
import java.util.Set;

@Component
public class DateUtils {

    private static final Set<MonthDay> FIXED_HOLIDAYS = new HashSet<>();

    static {
        FIXED_HOLIDAYS.add(MonthDay.of(1, 1));
        FIXED_HOLIDAYS.add(MonthDay.of(1, 2));
        FIXED_HOLIDAYS.add(MonthDay.of(1, 3));
        FIXED_HOLIDAYS.add(MonthDay.of(1, 4));
        FIXED_HOLIDAYS.add(MonthDay.of(1, 5));
        FIXED_HOLIDAYS.add(MonthDay.of(1, 6));
        FIXED_HOLIDAYS.add(MonthDay.of(1, 7));
        FIXED_HOLIDAYS.add(MonthDay.of(1, 8));
        FIXED_HOLIDAYS.add(MonthDay.of(2, 23));
        FIXED_HOLIDAYS.add(MonthDay.of(3, 8));
        FIXED_HOLIDAYS.add(MonthDay.of(5, 1));
        FIXED_HOLIDAYS.add(MonthDay.of(5, 9));
        FIXED_HOLIDAYS.add(MonthDay.of(6, 12));
        FIXED_HOLIDAYS.add(MonthDay.of(11, 4));
    }

    public int getWorkingDaysBetween(LocalDate start, LocalDate end) {
        int workingDays = 0;

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (isWorkingDay(date)) {
                workingDays++;
            }
        }

        return workingDays;
    }

    private boolean isWorkingDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate date) {
        return FIXED_HOLIDAYS.contains(MonthDay.from(date));
    }

}
