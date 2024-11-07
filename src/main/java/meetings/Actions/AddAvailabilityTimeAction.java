package meetings.Actions;

import meetings.Models.User;
import meetings.Services.ManagementAvailabilityTime.AvailabilityTimeService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class AddAvailabilityTimeAction implements Action {
    private final List<User> users;
    private final Scanner scanner;

    public AddAvailabilityTimeAction(List<User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Select user to add availability:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }

        int userIndex = Integer.parseInt(scanner.nextLine()) - 1;
        if (userIndex < 0 || userIndex >= users.size()) {
            System.out.println("Invalid user selected.");
            return;
        }

        User user = users.get(userIndex);
        System.out.print("Enter day of availability (e.g., MONDAY): ");
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter start time (HH:MM): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter end time (HH:MM): ");
        LocalTime endTime = LocalTime.parse(scanner.nextLine());

        AvailabilityTimeService availabilityTimeService = new AvailabilityTimeService();
        availabilityTimeService.add(user, dayOfWeek, startTime, endTime);

        System.out.println("Added availability for " + user.getName());
    }
}
