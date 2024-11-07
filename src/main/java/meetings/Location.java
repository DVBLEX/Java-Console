package meetings;

import java.time.ZoneId;

public class Location extends meetings.Models.Location {
    private final String address;
    private final ZoneId timeZone;

    public Location(String address, ZoneId timeZone) {
        super();
        this.address = address;
        this.timeZone = timeZone;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

}