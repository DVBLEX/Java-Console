package meetings.Actions;

import meetings.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserActionTest {

    private List<User> users;
    private Scanner scanner;
    private CreateUserAction action;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
    }

    @Test
    public void testExecuteWithValidInput() {
        String input = "John Doe\nEurope/Kiev\n";
        scanner = new Scanner(input);
        action = new CreateUserAction(users, scanner);

        action.execute();

        assertEquals(1, users.size());
        User createdUser = users.getFirst();
        assertEquals("John Doe", createdUser.getName());
        assertEquals(ZoneId.of("Europe/Kiev"), createdUser.getTimeZone());
    }

    @Test
    public void testExecuteWithInvalidTimeZone() {
        String input = "Jane Doe\nInvalid/TimeZone\n";
        scanner = new Scanner(input);
        action = new CreateUserAction(users, scanner);

        assertThrows(java.time.zone.ZoneRulesException.class, () -> action.execute());
        assertEquals(0, users.size());
    }
}
