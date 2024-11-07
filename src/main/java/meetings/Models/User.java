package meetings.Models;

import java.time.ZoneId;
import java.util.ArrayList;

public class User {
  String name;
  ZoneId timeZone;
  ArrayList<AvailabilityTime> availabilities;

  public User(String name, ZoneId timeZone) {
    this.name = name;
    this.timeZone = timeZone;
    this.availabilities = new ArrayList<>();
  }

  public ArrayList<AvailabilityTime> getAvailabilities() {
    return this.availabilities;
  }

  public ZoneId getTimeZone() {
    return this.timeZone;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return "User: " + this.name + ", TimeZone: " + this.timeZone + ", Availabilities: " + this.availabilities;
  }
}
