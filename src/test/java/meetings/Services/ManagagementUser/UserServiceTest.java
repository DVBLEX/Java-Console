package meetings.Services.ManagagementUser;

import meetings.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testAddUser() {
        String userName = "John Doe";
        ZoneId userZone = ZoneId.of("America/New_York");

        User user = userService.add(userName, userZone);

        assertNotNull(user);
        assertEquals(userName, user.getName());
        assertEquals(userZone, user.getTimeZone());
        assertNotNull(user.getAvailabilities());
        assertTrue(user.getAvailabilities().isEmpty());
    }
}
