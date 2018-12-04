package slng.fnord.Activities.HomeOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import slng.fnord.R;
import slng.fnord.Structures.Booking;


public class BookingListAdaptor extends BaseAdapter {
    private ArrayList<Booking> bookings;

    // override other abstract methods here

    public BookingListAdaptor(ArrayList<Booking> bookings){
        this.bookings = bookings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.booking_list_layout, container, false);
        }

        //TODO: Set more stuff to display to the user
        ((TextView) convertView.findViewById(R.id.service)).setText(getService(position));
        ((TextView) convertView.findViewById(R.id.startTime)).setText(getStartTime(position));
        ((TextView) convertView.findViewById(R.id.endTime)).setText(getEndTime(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return bookings.size();
    }

    public String getItem(int pos){
        return getService(pos);
    }

    private String getService(int pos){
        return bookings.get(pos).getService();
    }

    private String getEndTime(int pos){
        return ((Integer)bookings.get(pos).getEndTime()).toString();
    }

    private String getStartTime(int pos){
        return ((Integer)bookings.get(pos).getStartTime()).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
