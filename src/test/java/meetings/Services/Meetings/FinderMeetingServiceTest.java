package meetings.Services.Meetings;

import meetings.Models.AvailabilityTime;
import meetings.Models.Meeting;
import meetings.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FinderMeetingServiceTest {

  private FinderMeetingService finderMeetingService;

  @BeforeEach
  public void setUp() {
    List<User> users = new ArrayList<>();
    User user1 = new User("John", ZoneId.of("America/New_York"));
    user1.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(14, 0), LocalTime.of(18, 0)));

    User user2 = new User("Jane", ZoneId.of("America/New_York"));
    user2.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(16, 0)));

    users.add(user1);
    users.add(user2);

    finderMeetingService = new FinderMeetingService(users, ZoneId.of("America/New_York"));
  }

  @Test
  public void testFindOptimalTimeWithCommonAvailability() {
    Optional<Meeting> meeting = finderMeetingService.findOptimalTime(2);

    assertTrue(meeting.isPresent(), "Meeting should be found.");
    assertEquals(DayOfWeek.MONDAY, meeting.get().getDayOfWeek(), "The meeting should be scheduled on Monday.");
    assertEquals(LocalTime.of(14, 0), meeting.get().getStartTime(), "The meeting should start at 10:00.");
    assertEquals(2, meeting.get().getDuration(), "Meeting duration should be 2 hours.");
  }

  @Test
  public void testFindOptimalTimeWithNoCommonAvailability() {
    Optional<Meeting> meeting = finderMeetingService.findOptimalTime(4);

    assertFalse(meeting.isPresent(), "Meeting should not be found if no common time available.");
  }

  @Test
  public void testFindOptimalTimeWhenDurationTooLong() {
    Optional<Meeting> meeting = finderMeetingService.findOptimalTime(5);

    assertFalse(meeting.isPresent(), "Meeting should not be found when requested duration is longer than available time.");
  }

  @Test
  public void testFindOptimalTimeWithExactMatch() {
    Optional<Meeting> meeting = finderMeetingService.findOptimalTime(1);

    assertTrue(meeting.isPresent(), "Meeting should be found.");
    assertEquals(DayOfWeek.MONDAY, meeting.get().getDayOfWeek(), "The meeting should be scheduled on Monday.");
    assertEquals(LocalTime.of(14, 0), meeting.get().getStartTime(), "The meeting should start at 10:00.");
    assertEquals(1, meeting.get().getDuration(), "Meeting duration should be 1 hour.");
  }
}
