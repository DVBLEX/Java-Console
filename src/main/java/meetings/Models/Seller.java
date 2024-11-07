package meetings.Models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

public class Seller {
    String name;
    public ZoneId timeZone;
    Map<DayOfWeek, LocalTime[]> workSchedule;
    List<LocalDate> holidays;
    public Seller(String name, ZoneId timeZone, Map<DayOfWeek, LocalTime[]> workSchedule, List<LocalDate> holidays) {
        this.name = name;
        this.timeZone = timeZone;
        this.workSchedule = workSchedule;
        this.holidays = holidays;
    }
    public boolean isWorkingDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return workSchedule.containsKey(day) && !holidays.contains(date);
    }
    public ZoneId getTimeZone() {
        return this.timeZone;
    }
    public String getName() {
        return name;
    }
}