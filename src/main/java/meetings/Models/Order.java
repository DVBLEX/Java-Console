package meetings.Models;

import java.time.LocalDate;

class Order {
    LocalDate orderDate;
    Seller seller;
    DeliveryMethod deliveryMethod;
    Location deliveryLocation;
    public Order(LocalDate orderDate, Seller seller, DeliveryMethod deliveryMethod, Location deliveryLocation) {
        this.orderDate = orderDate;
        this.seller = seller;
        this.deliveryMethod = deliveryMethod;
        this.deliveryLocation = deliveryLocation;
    }
}
