package meetings.Actions;

import meetings.Models.User;
import meetings.Services.ManagagementUser.UserService;

import java.time.ZoneId;
import java.util.List;
import java.util.Scanner;

public class CreateUserAction implements Action {
    private final List<User> users;
    private final Scanner scanner;

    public CreateUserAction(List<User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        UserService service = new UserService();
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter user timezone (e.g., Europe/Kiev): ");
        String timeZone = scanner.nextLine();
        User user = service.add(name, ZoneId.of(timeZone));
        users.add(user);
        System.out.println("User created: " + user);
    }
}
