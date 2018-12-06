package slng.fnord.Activities.HomeOwner;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Activities.Shared.Welcome;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.BookingListAdaptor;
import slng.fnord.Managers.BookingManager;
import slng.fnord.R;
import slng.fnord.Structures.Booking;
import slng.fnord.Structures.HomeOwner;
import slng.fnord.Structures.ServiceProvider;

public class BookingList extends Activity {
    private ListView bookingList;
    private BookingListAdaptor bookingListAdaptor;
    private ArrayList<Booking> bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobooking_list);

        // list of bookings setup
        BookingManager bookingManager = new BookingManager(new DBHelper());
        bookingManager.getAllBookings(Welcome.currentUser.getEmail(), this::bookingListCallback);
        bookingList = (ListView) findViewById(R.id.listOfBookings);

    }

    public void bookingListCallback(ArrayList<Booking> bookings){
        this.bookings = bookings;
        bookingListAdaptor = new BookingListAdaptor(bookings, this);
        bookingList.setAdapter(bookingListAdaptor);
    }

    public void onBookingItemClick(View v){
        int pos = Integer.parseInt((String) ((TextView) v.findViewById(R.id.bookingLPosition)).getText());
        Booking b = bookings.get(pos);
        Intent intent = null;
        intent = new Intent(this, BookService.class);
        this.startActivity(intent);
    }

    public void openBookingActivity(Booking booking){
        BookingReview.booking=booking;
        Intent intent = null;
        intent = new Intent(this, BookingReview.class);
        this.startActivity(intent);
    }

}
