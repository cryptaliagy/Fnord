package slng.fnord.Activities.HomeOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Optional;

import io.reactivex.functions.Consumer;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.BookingManager;
import slng.fnord.R;
import slng.fnord.Structures.Booking;
import slng.fnord.Structures.User;


public class BookingListAdaptor extends BaseAdapter {
    private ArrayList<Booking> bookings;
    private BookingManager bookingManager;

    // override other abstract methods here

    public BookingListAdaptor(ArrayList<Booking> bookings){

        this.bookings = bookings;
        bookingManager = new BookingManager(new DBHelper());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.booking_list_layout, container, false);
        }

        //TODO: Set more stuff to display to the user
         getBooking(position, (Optional<Booking> b)->{
            if (b.isPresent()){
                fillItem(position, convertView, b.get());
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return bookings.size();
    }

    public void fillItem(int pos, View v, Booking booking){
        ((TextView) v.findViewById(R.id.bookingLService)).setText(booking.getService());
        ((TextView) v.findViewById(R.id.bookingLStartTime)).setText(booking.getStartTime()+":00");
        ((TextView) v.findViewById(R.id.bookingLEndTime)).setText(booking.getEndTime()+":00");
    }

    public String getItem(int pos){
        return getService(pos);
    }

    public Booking getBooking(int pos){return bookings.get(pos);}

    public void getBooking(int pos, Consumer<Optional<Booking>> callback){
        //bookingManager.getBooking(getItem(pos), callback);
    }

    private String getService(int pos){
        return bookings.get(pos).getService();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
