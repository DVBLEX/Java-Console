package meetings.Services.ManagementAvailabilityTime;

import meetings.Models.AvailabilityTime;
import meetings.Models.User;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AvailabilityTimeService {
  public AvailabilityTime add(User targetUser, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
    if (endTime.isBefore(startTime)) {
      return null;
    }

    AvailabilityTime availabilityTime = new AvailabilityTime(dayOfWeek, startTime, endTime);
    targetUser.getAvailabilities().add(availabilityTime);

    return availabilityTime;
  }
}
