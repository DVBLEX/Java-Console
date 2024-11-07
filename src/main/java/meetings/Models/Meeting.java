package meetings.Models;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Meeting {
  int duration;
  DayOfWeek dayOfWeek;
  LocalTime startTime;

  public Meeting(int duration, DayOfWeek dayOfWeek, LocalTime startTime) {
    this.duration = duration;
    this.dayOfWeek = dayOfWeek;
    this.startTime = startTime;
  }

  public int getDuration() {
    return this.duration;
  }

  public DayOfWeek getDayOfWeek() {
    return this.dayOfWeek;
  }

  public LocalTime getStartTime() {
    return this.startTime;
  }

  @Override
  public String toString() {
    return "Meeting: " + this.dayOfWeek + ", Start: " + this.startTime + ", Duration: " + this.duration + " hours";
  }
}
