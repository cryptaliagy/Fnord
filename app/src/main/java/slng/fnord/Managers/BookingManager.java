package slng.fnord.Managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import slng.fnord.Helpers.Common;
import slng.fnord.Helpers.DBOberver;
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

        String id = database.addBooking(booking);
        user.addBooking(id);
        provider.addBooking(id);
        database.updateUser(user);
        database.updateUser(provider);
    }

    public void getBooking(String id, Consumer<Booking> callback) {
        database.getBooking(id).subscribe(new DBOberver<Optional<Booking>>() {
            @Override
            public void onNext(Optional<Booking> booking) {
                Booking extracted = Common.extractOptional(booking);
                try {
                    callback.accept(extracted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void removeBooking(String id) {
        database.removeBooking(id);
    }

    public void getAllBookings(String email, Consumer<ArrayList<Booking>> callback) {
        ArrayList<Booking> allBookings = new ArrayList<>();
        Disposable disposable;
        database.getAllBookings()
                // Filters the bookings by email of the user
                .filter(booking -> booking.getServiceProviderInfo().getEmail().equals(email)
                        || booking.getHomeOwnerInfo().getEmail().equals(email))
                // Adds the filtered booking to the array list
                .subscribe(new DBOberver<Booking>() {
                    @Override
                    public void onNext(Booking booking) {
                        allBookings.add(booking);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        try {
                            callback.accept(allBookings);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
