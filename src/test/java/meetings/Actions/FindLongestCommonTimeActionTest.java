package meetings.Actions;

import meetings.Models.AvailabilityTime;
import meetings.Models.User;
import meetings.Services.Meetings.TimeTogetherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindLongestCommonTimeActionTest {

    private List<User> users;
    private ZoneId timeZone;
    private FindLongestCommonTimeAction action;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        timeZone = ZoneId.of("America/New_York");
    }

    @Test
    public void testExecuteWithCommonAvailability() {
        User user1 = new User("John", timeZone);
        user1.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));

        User user2 = new User("Jane", timeZone);
        user2.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(13, 0)));

        users.add(user1);
        users.add(user2);

        action = new FindLongestCommonTimeAction(users, timeZone);

        action.execute();

        TimeTogetherService timeTogetherService = new TimeTogetherService(users, timeZone);
        int maxTime = timeTogetherService.findGreatestTime();
        assertEquals(2, maxTime, "The longest common available time should be 2 hours.");
    }

    @Test
    public void testExecuteWithNoCommonAvailability() {
        User user1 = new User("John", timeZone);
        user1.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)));

        User user2 = new User("Jane", timeZone);
        user2.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)));

        users.add(user1);
        users.add(user2);

        action = new FindLongestCommonTimeAction(users, timeZone);

        action.execute();

        TimeTogetherService timeTogetherService = new TimeTogetherService(users, timeZone);
        int maxTime = timeTogetherService.findGreatestTime();
        assertEquals(0, maxTime, "There should be no common available time.");
    }

    @Test
    public void testExecuteWithSingleUser() {
        User user1 = new User("John", timeZone);
        user1.getAvailabilities().add(new AvailabilityTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));

        users.add(user1);

        action = new FindLongestCommonTimeAction(users, timeZone);

        action.execute();

        TimeTogetherService timeTogetherService = new TimeTogetherService(users, timeZone);
        int maxTime = timeTogetherService.findGreatestTime();
        assertEquals(3, maxTime, "The available time for a single user should be 3 hours.");
    }
}
