package meetings.Actions;

import meetings.Models.Meeting;
import meetings.Models.User;
import meetings.Services.Meetings.FinderMeetingService;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FindMeetingAction implements Action {
    private final List<User> users;
    private final ZoneId timeZone;
    private final Scanner scanner;

    public FindMeetingAction(List<User> users, ZoneId timeZone, Scanner scanner) {
        this.users = users;
        this.timeZone = timeZone;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        FinderMeetingService finderMeeting = new FinderMeetingService(users, timeZone);
        System.out.print("Enter meeting duration in hours: ");
        int duration = Integer.parseInt(scanner.nextLine());

        Optional<Meeting> meeting = finderMeeting.findOptimalTime(duration);
        System.out.println(meeting.orElse(null));
    }
}
