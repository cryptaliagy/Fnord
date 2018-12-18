package slng.fnord.Managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBObserver;
import slng.fnord.Database.Interfaces.Database;
import slng.fnord.Structures.Service.Booking;
import slng.fnord.Structures.User.HomeOwner;
import slng.fnord.Structures.User.ServiceProvider;

public class BookingManager {
    private Database database;
    private static BookingManager instance = new BookingManager();


    /**
     * Singleton instance retriever (with proper connection to the database)
     * @return singleton instance of the Manager object
     */
    public static BookingManager getInstance() {
        return instance;
    }

    /**
     * Constructor; necessary to prevent instantiating Manager and set up proper DB object
     */
    private BookingManager() {
        this.database = DBHelper.getInstance();
    }

    /**
     * Constructor; initializes the object with the specified database instance
     * Kept for testing purposes only
     * @param database
     */

    @Deprecated
    public BookingManager(Database database) {
        this.database = database;
    }

    public void makeBooking(HomeOwner user, ServiceProvider provider, String service,
                            Calendar date, int startTime, int endTime) {
        Booking booking = new Booking(provider, user, service, date, startTime, endTime);

        database.addBooking(booking);
        user.addBooking(booking.getId());
        provider.addBooking(booking.getId());
        database.updateUser(user);
        database.updateUser(provider);
    }

    public void getBooking(String id, Consumer<Booking> callback) {
        database.getBooking(id).subscribe(new DBObserver<Optional<Booking>>() {
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
                .subscribe(new DBObserver<Booking>() {
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
