package meetings;


import meetings.Actions.*;
import meetings.Models.DeliveryCalculator;
import meetings.Models.DeliveryMethod;
import meetings.Models.Seller;
import meetings.Models.User;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Seller> sellers = initSellers();
        List<DeliveryMethod> deliveryMethods = initDeliveryMethods();
        boolean running = true;

        while (running) {
            printMenu();
            int action = Integer.parseInt(scanner.nextLine());

            switch (action) {
                case 1:

                    System.out.println("Enter the order date (YYYY-MM-DD):");
                    LocalDate orderDate = LocalDate.parse(scanner.nextLine());

                    System.out.println("Order date recorded: " + orderDate);
                    break;

                case 2:

                    System.out.println("Choose a seller based on their work schedule:");

                    Seller selectedSeller = sellers.get(0);
                    System.out.println("Selected Seller: " + selectedSeller.getName());
                    break;

                case 3:

                    System.out.println("Calculating average delivery time for each seller...");

                    break;

                case 4:

                    Action calculateDeliveryAction = new CalculateDeliveryAction(sellers, deliveryMethods, scanner);
                    calculateDeliveryAction.execute();
                    break;

                case 5:

                    System.out.println("Assessing impact of holidays on delivery schedules...");

                    break;

                case 6:

                    System.out.println("Considering time zones for delivery and store locations...");

                    break;

                case 7:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Choose an option for delivery calculation:");
        System.out.println("1. Enter Order Date");
        System.out.println("2. Choose Seller based on Work Schedule");
        System.out.println("3. Calculate Average Delivery Time per Seller");
        System.out.println("4. Select Delivery Method and Calculate Estimated Delivery Date");
        System.out.println("5. Assess Impact of Holidays on Delivery Schedule");
        System.out.println("6. Consider Time Zones for Delivery and Store Locations");
        System.out.println("7. Exit");
    }

    private static List<Seller> initSellers() {

        List<Seller> sellers = new ArrayList<>();
        Map<DayOfWeek, LocalTime[]> workSchedule = new HashMap<>();
        workSchedule.put(DayOfWeek.MONDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(18, 0)});
        workSchedule.put(DayOfWeek.TUESDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(18, 0)});


        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(2023, Month.DECEMBER, 25) // Example holiday
        );

        sellers.add(new Seller("Seller1", ZoneId.of("Europe/Paris"), workSchedule, holidays));
        return sellers;
    }

    private static List<DeliveryMethod> initDeliveryMethods() {

        List<DeliveryMethod> methods = new ArrayList<>();
        methods.add(new DeliveryMethod("Standard Delivery", Duration.ofDays(3), Duration.ofDays(5)));
        methods.add(new DeliveryMethod("Express Delivery", Duration.ofDays(1), Duration.ofDays(2)));
        return methods;
    }
}


class CalculateDeliveryAction implements Action {
    private final List<Seller> sellers;
    private final List<DeliveryMethod> deliveryMethods;
    private final Scanner scanner;

    public CalculateDeliveryAction(List<Seller> sellers, List<DeliveryMethod> deliveryMethods, Scanner scanner) {
        this.sellers = sellers;
        this.deliveryMethods = deliveryMethods;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the order date (YYYY-MM-DD):");
        LocalDate orderDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Choose a seller:");

        Seller selectedSeller = sellers.get(0);
        System.out.println("Choose a delivery method:");

        DeliveryMethod selectedMethod = deliveryMethods.get(0);

        DeliveryCalculator calculator = new DeliveryCalculator(sellers, deliveryMethods);
        LocalDate deliveryDate = calculator.calculateNextBusinessDay(orderDate, selectedSeller, selectedMethod,
                new Location("Store", selectedSeller.getTimeZone()),
                new Location("Customer", ZoneId.systemDefault()));
        System.out.println("Estimated delivery date: " + deliveryDate);
    }
}
