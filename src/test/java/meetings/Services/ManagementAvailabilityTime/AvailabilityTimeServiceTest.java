package meetings.Services.ManagementAvailabilityTime;

import meetings.Models.AvailabilityTime;
import meetings.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityTimeServiceTest {

  private AvailabilityTimeService availabilityTimeService;
  private User testUser;

  @BeforeEach
  public void setUp() {
    availabilityTimeService = new AvailabilityTimeService();
    testUser = new User("John Doe", ZoneId.of("America/New_York"));
  }

  @Test
  public void testAddValidAvailabilityTime() {
    DayOfWeek dayOfWeek = DayOfWeek.TUESDAY;
    LocalTime startTime = LocalTime.of(9, 0);
    LocalTime endTime = LocalTime.of(17, 0);

    AvailabilityTime result = availabilityTimeService.add(testUser, dayOfWeek, startTime, endTime);

    assertNotNull(result, "AvailabilityTime should be added successfully.");
    assertEquals(dayOfWeek, result.getDay(), "Day of week should match.");
    assertEquals(startTime, result.getStartTime(), "Start time should match.");
    assertEquals(endTime, result.getEndTime(), "End time should match.");
    assertEquals(1, testUser.getAvailabilities().size(), "User should have one availability.");
    assertTrue(testUser.getAvailabilities().contains(result), "Availability should be added to the user.");
  }

  @Test
  public void testAddInvalidAvailabilityTimeEndBeforeStart() {
    DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
    LocalTime startTime = LocalTime.of(17, 0);
    LocalTime endTime = LocalTime.of(9, 0);

    AvailabilityTime result = availabilityTimeService.add(testUser, dayOfWeek, startTime, endTime);

    assertNull(result, "AvailabilityTime should be null when end time is before start time.");
    assertEquals(0, testUser.getAvailabilities().size(), "User should not have any availability added.");
  }
}
