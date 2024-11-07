package meetings.Models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class DeliveryMethod {
    String name;
    Duration averageTime;
    Duration maxDeadline;

    public DeliveryMethod(String name, Duration averageTime, Duration maxDeadline) {
        this.name = name;
        this.averageTime = averageTime;
        this.maxDeadline = maxDeadline;
    }

    public Duration getActualDeliveryTime(LocalDate orderDate, Seller seller, Location storeLocation, Location deliveryLocation) {

        Duration travelFactor = Duration.ofHours(estimateTravelTime(storeLocation, deliveryLocation));

        LocalDateTime localOrderDateTime = orderDate.atStartOfDay();
        ZonedDateTime storeZonedDateTime = localOrderDateTime.atZone(storeLocation.getTimeZone());
        ZonedDateTime deliveryZonedDateTime = localOrderDateTime.atZone(deliveryLocation.getTimeZone());

        Duration timeZoneDifference = Duration.between(storeZonedDateTime.toInstant(), deliveryZonedDateTime.toInstant());

        Duration adjustedDeliveryTime = averageTime.plus(travelFactor).plus(timeZoneDifference);

        return adjustedDeliveryTime.compareTo(maxDeadline) <= 0 ? adjustedDeliveryTime : maxDeadline;
    }

    private long estimateTravelTime(Location from, Location to) {

        return 2;
    }
}