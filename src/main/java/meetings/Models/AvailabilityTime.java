package meetings.Models;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AvailabilityTime {
  DayOfWeek dayOfWeek;
  LocalTime startTime;
  LocalTime endTime;

  public AvailabilityTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
    this.dayOfWeek = dayOfWeek;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public DayOfWeek getDay() {
    return this.dayOfWeek;
  }

  public LocalTime getStartTime() {
      return this.startTime;
  }

  public LocalTime getEndTime() {
      return this.endTime;
  }

  @Override
  public String toString() {
    return this.dayOfWeek + ": " + this.startTime + " - " + this.endTime;
  }
}
