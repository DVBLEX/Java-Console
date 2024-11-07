package meetings.Services.Meetings;

import meetings.Models.AvailabilityTime;
import meetings.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTogetherServiceTest {

  private TimeTogetherService timeTogetherService;
  private List<User> users;

  @BeforeEach
  public void setUp() {
    users = new ArrayList<>();

    User user1 = new User("John", ZoneId.of("America/New_York"));
    user1.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)));

    User user2 = new User("Jane", ZoneId.of("America/New_York"));
    user2.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(18, 0)));

    users.add(user1);
    users.add(user2);

    timeTogetherService = new TimeTogetherService(users, ZoneId.of("America/New_York"));
  }

  @Test
  public void testFindGreatestTimeWithCommonAvailability() {
    int greatestTime = timeTogetherService.findGreatestTime();
    assertEquals(8, greatestTime, "Greatest common time should be 8 hours.");
  }

  @Test
  public void testFindGreatestTimeWithNoCommonAvailability() {
    User user3 = new User("Jack", ZoneId.of("America/New_York"));
    user3.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0)));
    users.add(user3);

    int greatestTime = timeTogetherService.findGreatestTime();
    assertEquals(0, greatestTime, "There should be no common time, so the result should be 0.");
  }

  @Test
  public void testFindGreatestTimeWhenOnlyOneUserAvailable() {
    users.clear();
    User user = new User("John", ZoneId.of("America/New_York"));
    user.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));
    users.add(user);

    timeTogetherService = new TimeTogetherService(users, ZoneId.of("America/New_York"));
    int greatestTime = timeTogetherService.findGreatestTime();
    assertEquals(3, greatestTime, "The only user's availability is 3 hours, so it should return 3 hours.");
  }

  @Test
  public void testFindGreatestTimeWithExactMatch() {
    users.clear();
    User user1 = new User("John", ZoneId.of("America/New_York"));
    user1.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));

    User user2 = new User("Jane", ZoneId.of("America/New_York"));
    user2.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));

    users.add(user1);
    users.add(user2);

    timeTogetherService = new TimeTogetherService(users, ZoneId.of("America/New_York"));
    int greatestTime = timeTogetherService.findGreatestTime();
    assertEquals(3, greatestTime, "Both users have the same availability for 3 hours, so it should return 3 hours.");
  }

  @Test
  public void testFindGreatestTimeAcrossDifferentDays() {

    User user1 = users.get(0);
    user1.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));

    User user2 = users.get(1);
    user2.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)));

    int greatestTime = timeTogetherService.findGreatestTime();
    assertEquals(2, greatestTime, "The greatest common time across different days should be 2 hours on Monday.");
  }
}
