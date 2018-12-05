package slng.fnord.Activities.HomeOwner;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Activities.Shared.Welcome;
import slng.fnord.R;
import slng.fnord.Structures.Booking;
import slng.fnord.Structures.HomeOwner;
import slng.fnord.Structures.ServiceProvider;

public class BookingList extends Activity {
    private ListView bookingList;
    private BookingListAdaptor bookingListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobooking_list);

        bookingList = (ListView) findViewById(R.id.listOfBookings);

        //TODO: get bookings from User class
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        ServiceProvider sp = new ServiceProvider("lmao", "cool");
        bookings.add(new Booking(sp, (HomeOwner)Welcome.currentUser, "service",Calendar.getInstance(), 42, 55));

        bookingListAdaptor = new BookingListAdaptor(bookings);
        bookingList.setAdapter(bookingListAdaptor);

    }

}
