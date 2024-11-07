package meetings.Services.Meetings;

import meetings.Models.AvailabilityTime;
import meetings.Models.User;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TimeTogetherService extends BaseService {
  public TimeTogetherService(List<User> users, ZoneId TimeZone) {
    super(users, TimeZone);
  }

  public int findGreatestTime() {
    Map<DayOfWeek, List<AvailabilityTime>> mergedAvailabilities = getMapOfAvailabilities();

    long maxDurationInMinutes = 0;

    for (DayOfWeek day : mergedAvailabilities.keySet()) {
      List<AvailabilityTime> dayAvailabilities = mergedAvailabilities.get(day);

      if (allUsersHaveTime(dayAvailabilities, day)) {
        continue;
      }

      dayAvailabilities.sort(Comparator.comparing(AvailabilityTime::getStartTime));

      LocalTime latestStartTime = LocalTime.MIN;
      LocalTime earliestEndTime = LocalTime.MAX;

      for (AvailabilityTime availability : dayAvailabilities) {
        latestStartTime = latestStartTime.isAfter(availability.getStartTime()) ? latestStartTime : availability.getStartTime();
        earliestEndTime = earliestEndTime.isBefore(availability.getEndTime()) ? earliestEndTime : availability.getEndTime();
      }

      long duration = Duration.between(latestStartTime, earliestEndTime).toMinutes();

      if (duration > 0 && duration > maxDurationInMinutes) {
        maxDurationInMinutes = duration;
      }
    }

    return (int) (maxDurationInMinutes / 60);
  }
}
