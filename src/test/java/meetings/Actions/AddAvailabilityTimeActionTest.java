package meetings.Actions;

import meetings.Models.AvailabilityTime;
import meetings.Models.User;
import meetings.Services.ManagementAvailabilityTime.AvailabilityTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AddAvailabilityTimeActionTest {

    private List<User> users;
    private Scanner scanner;
    private AvailabilityTimeService availabilityTimeService;
    private AddAvailabilityTimeAction action;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        users.add(new User("John", null));
        users.add(new User("Jane", null));
        availabilityTimeService = new AvailabilityTimeService();
    }

    @Test
    public void testExecuteWithValidInput() {
        String input = "1\nMONDAY\n09:00\n12:00\n";
        scanner = new Scanner(input);
        action = new AddAvailabilityTimeAction(users, scanner);

        action.execute();

        assertEquals(1, users.getFirst().getAvailabilities().size());
        AvailabilityTime availability = users.getFirst().getAvailabilities().getFirst();
        assertEquals(DayOfWeek.MONDAY, availability.getDay());
        assertEquals(LocalTime.of(9, 0), availability.getStartTime());
        assertEquals(LocalTime.of(12, 0), availability.getEndTime());
    }

    @Test
    public void testExecuteWithInvalidUserSelection() {
        String input = "3\n";
        scanner = new Scanner(input);
        action = new AddAvailabilityTimeAction(users, scanner);

        action.execute();

        assertEquals(0, users.get(0).getAvailabilities().size());
        assertEquals(0, users.get(1).getAvailabilities().size());
    }

    @Test
    public void testExecuteWithInvalidDayOfWeek() {
        String input = "1\nINVALID_DAY\n09:00\n12:00\n";
        scanner = new Scanner(input);
        action = new AddAvailabilityTimeAction(users, scanner);

        assertThrows(IllegalArgumentException.class, () -> action.execute());
        assertEquals(0, users.getFirst().getAvailabilities().size());
    }

    @Test
    public void testExecuteWithInvalidTimeFormat() {
        String input = "1\nMONDAY\nINVALID_TIME\n12:00\n";
        scanner = new Scanner(input);
        action = new AddAvailabilityTimeAction(users, scanner);

        assertThrows(java.time.format.DateTimeParseException.class, () -> action.execute());
        assertEquals(0, users.getFirst().getAvailabilities().size());
    }
}
