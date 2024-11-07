package meetings.Services.Meetings;

import meetings.Models.AvailabilityTime;
import meetings.Models.Meeting;
import meetings.Models.User;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FinderMeetingService extends BaseService {
  public FinderMeetingService(List<User> users, ZoneId TimeZone) {
    super(users, TimeZone);
  }

  public Optional<Meeting> findOptimalTime(int durationHours) {
    Map<DayOfWeek, List<AvailabilityTime>> mergedAvailabilities = getMapOfAvailabilities();

    for (DayOfWeek day : mergedAvailabilities.keySet()) {
      List<AvailabilityTime> dayAvailabilities = mergedAvailabilities.get(day);
      
      if (allUsersHaveTime(dayAvailabilities, day)) {
        continue;
      }

      dayAvailabilities.sort(Comparator.comparing(AvailabilityTime::getStartTime));

      Optional<LocalTime> commonStart = findCommonStartTime(dayAvailabilities, durationHours);
      
      if (commonStart.isPresent()) {
        return Optional.of(new Meeting(durationHours, day, commonStart.get()));
      }
    }

    return Optional.empty();
  }

  private Optional<LocalTime> findCommonStartTime(List<AvailabilityTime> dayAvailabilities, int durationHours) {
    LocalTime latestStartTime = LocalTime.MIN;
    LocalTime earliestEndTime = LocalTime.MAX;

    for (AvailabilityTime availability : dayAvailabilities) {
      latestStartTime = latestStartTime.isAfter(availability.getStartTime()) ? latestStartTime : availability.getStartTime();
      earliestEndTime = earliestEndTime.isBefore(availability.getEndTime()) ? earliestEndTime : availability.getEndTime();
    }

    if (Duration.between(latestStartTime, earliestEndTime).toHours() >= durationHours) {
      return Optional.of(latestStartTime);
    }

    return Optional.empty();
  }
}
