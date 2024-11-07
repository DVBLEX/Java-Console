package meetings.Services.ManagagementUser;
import meetings.Models.User;

import java.time.ZoneId;

public class UserService {
    public User add(String name, ZoneId zoneId) {
        return new User(name, zoneId);
    }
}
