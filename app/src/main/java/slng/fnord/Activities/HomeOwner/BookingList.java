package slng.fnord.Activities.HomeOwner;

import android.os.Bundle;
import android.app.Activity;

import slng.fnord.R;
import slng.fnord.Structures.Booking;

public class BookingList extends Activity {
    //somewhere in this class, when a booking is picked from the list, that booking is assigned to currentBooking
    //then the activity BookingReview.java (activity_hobooking_review.xml) is started
    public static Booking currentBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobooking_list);
    }

}
