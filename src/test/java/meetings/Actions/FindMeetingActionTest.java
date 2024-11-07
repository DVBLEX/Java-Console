package meetings.Actions;

import meetings.Models.AvailabilityTime;
import meetings.Models.Meeting;
import meetings.Models.User;
import meetings.Services.Meetings.FinderMeetingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FindMeetingActionTest {

    private List<User> users;
    private ZoneId timeZone;
    private Scanner scanner;
    private FindMeetingAction action;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        timeZone = ZoneId.of("America/New_York");
    }

    private void addUserAvailability(User user, DayOfWeek day, LocalTime start, LocalTime end) {
        user.getAvailabilities().add(new AvailabilityTime(day, start, end));
        users.add(user);
    }

    @Test
    public void testExecuteWithValidMeeting() {
        addUserAvailability(new User("John", timeZone), DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0));
        addUserAvailability(new User("Jane", timeZone), DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(13, 0));

        scanner = new Scanner("2\n");
        action = new FindMeetingAction(users, timeZone, scanner);
        action.execute();

        FinderMeetingService finderMeetingService = new FinderMeetingService(users, timeZone);
        Optional<Meeting> meeting = finderMeetingService.findOptimalTime(2);

        assertTrue(meeting.isPresent(), "Meeting should be found.");
        assertEquals(DayOfWeek.MONDAY, meeting.get().getDayOfWeek());
        assertEquals(LocalTime.of(10, 0), meeting.get().getStartTime());
        assertEquals(2, meeting.get().getDuration());
    }

    @Test
    public void testExecuteWithNoAvailableMeeting() {
        addUserAvailability(new User("John", timeZone), DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));
        addUserAvailability(new User("Jane", timeZone), DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 0));

        scanner = new Scanner("2\n");
        action = new FindMeetingAction(users, timeZone, scanner);
        action.execute();

        FinderMeetingService finderMeetingService = new FinderMeetingService(users, timeZone);
        Optional<Meeting> meeting = finderMeetingService.findOptimalTime(2);

        assertFalse(meeting.isPresent(), "No meeting should be found.");
    }

    @Test
    public void testExecuteWithExactMeetingDuration() {
        addUserAvailability(new User("John", timeZone), DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0));
        addUserAvailability(new User("Jane", timeZone), DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0));

        scanner = new Scanner("2\n");
        action = new FindMeetingAction(users, timeZone, scanner);
        action.execute();

        FinderMeetingService finderMeetingService = new FinderMeetingService(users, timeZone);
        Optional<Meeting> meeting = finderMeetingService.findOptimalTime(2);

        assertTrue(meeting.isPresent(), "Meeting should be found.");
        assertEquals(DayOfWeek.MONDAY, meeting.get().getDayOfWeek());
        assertEquals(LocalTime.of(9, 0), meeting.get().getStartTime());
        assertEquals(2, meeting.get().getDuration());
    }

    @Test
    public void testExecuteWithInvalidDuration() {
        addUserAvailability(new User("John", timeZone), DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0));
        addUserAvailability(new User("Jane", timeZone), DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0));

        scanner = new Scanner("abc\n");
        action = new FindMeetingAction(users, timeZone, scanner);

        assertThrows(NumberFormatException.class, action::execute);
    }
}
