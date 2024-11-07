package meetings.Actions;

import meetings.Models.User;
import meetings.Services.Meetings.TimeTogetherService;

import java.time.ZoneId;
import java.util.List;

public class FindLongestCommonTimeAction implements Action {
    private final List<User> users;
    private final ZoneId timeZone;

    public FindLongestCommonTimeAction(List<User> users, ZoneId timeZone) {
        this.users = users;
        this.timeZone = timeZone;
    }

    @Override
    public void execute() {
        TimeTogetherService timeTogetherService = new TimeTogetherService(users, timeZone);
        int maxTime = timeTogetherService.findGreatestTime();
        System.out.println("Longest common available time for all users: " + maxTime + " hours");
    }
}
