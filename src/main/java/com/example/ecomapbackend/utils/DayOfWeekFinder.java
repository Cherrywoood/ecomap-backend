package com.example.ecomapbackend.utils;

import com.example.ecomapbackend.enums.DayWeek;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class DayOfWeekFinder {
    private DayOfWeekFinder() {}

    public static DayWeek getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY -> DayWeek.MON;
            case Calendar.TUESDAY -> DayWeek.TUE;
            case Calendar.WEDNESDAY -> DayWeek.WED;
            case Calendar.THURSDAY -> DayWeek.THU;
            case Calendar.FRIDAY -> DayWeek.FRI;
            case Calendar.SATURDAY -> DayWeek.SAT;
            case Calendar.SUNDAY -> DayWeek.SUN;
            default -> throw new IllegalStateException("Unexpected value: " + calendar.get(Calendar.DAY_OF_WEEK));
        };
    }
}
