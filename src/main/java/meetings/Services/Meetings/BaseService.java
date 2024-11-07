package meetings.Services.Meetings;

import meetings.Models.AvailabilityTime;
import meetings.Models.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseService {
  List<User> users;
  ZoneId TimeZone;

  public BaseService(List<User> users, ZoneId TimeZone) {
    this.users = users;
    this.TimeZone = TimeZone;
  }

  public Map<DayOfWeek, List<AvailabilityTime>> getMapOfAvailabilities() {
    Map<DayOfWeek, List<AvailabilityTime>> mergedAvailabilities = new HashMap<>();

    for (DayOfWeek day : DayOfWeek.values()) {
      mergedAvailabilities.put(day, new ArrayList<>());
    }

    for (User user : users) {
      for (AvailabilityTime availability : user.getAvailabilities()) {
        ZonedDateTime startZoned = ZonedDateTime.of(LocalDate.now(), availability.getStartTime(), user.getTimeZone())
                                                .withZoneSameInstant(TimeZone);
        ZonedDateTime endZoned = ZonedDateTime.of(LocalDate.now(), availability.getEndTime(), user.getTimeZone())
                                                .withZoneSameInstant(TimeZone);
      
        mergedAvailabilities.get(availability.getDay())
                            .add(new AvailabilityTime(availability.getDay(), startZoned.toLocalTime(), endZoned.toLocalTime()));
      }
    }

    return mergedAvailabilities;
  }

  public boolean allUsersHaveTime(List<AvailabilityTime> availabilities, DayOfWeek day) {
    for (User user : users) {
      boolean hasCommonTime = availabilities.stream()
          .filter(availability -> availability.getDay().equals(day))
          .anyMatch(availability -> isWithinCommonTime(user, availability));

      if (!hasCommonTime) {
        return true;
      }
    }
    return false;
  }

  public boolean isWithinCommonTime(User user, AvailabilityTime targetAvailability) {
    List<AvailabilityTime> userAvailabilities = user.getAvailabilities();
    for (AvailabilityTime userAvailability : userAvailabilities) {
      if (userAvailability.getDay().equals(targetAvailability.getDay())) {
        if (!userAvailability.getEndTime().isBefore(targetAvailability.getStartTime()) && 
            !userAvailability.getStartTime().isAfter(targetAvailability.getEndTime())) {
          return true;
        }
      }
    }
    return false;
  }

  public List<User> getUsers() {
    return this.users;
  }
}
