package slng.fnord.Managers;

import java.util.Calendar;

import io.reactivex.functions.Consumer;
import slng.fnord.Helpers.Interfaces.Database;
import slng.fnord.Structures.Booking;
import slng.fnord.Structures.HomeOwner;
import slng.fnord.Structures.ServiceProvider;

public class BookingManager {
    private Database database;

    public BookingManager(Database database) {
        this.database = database;
    }

    public void makeBooking(HomeOwner user, ServiceProvider provider, String service,
                            Calendar date, int startTime, int endTime) {
        Booking booking = new Booking(provider, user, service, date, startTime, endTime);

        database.addBooking(booking);
        user.addBooking(booking.getId());
        provider.addBooking(booking.getId());
    }
}
