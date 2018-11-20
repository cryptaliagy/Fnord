package slng.fnord;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.provider.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.util.HashMap;

public class Availability extends AppCompatActivity {
    private CalendarView cal = null;

    private HashMap<Long, AvailabilityEnum[]> savedSlots= new HashMap<Long, AvailabilityEnum[]>();

    private AvailabilityEnum[] timeSlots;
    private long lastDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        timeSlots = new AvailabilityEnum[24];

        cal = (CalendarView) findViewById(R.id.availabilityCal);
        lastDate = cal.getDate();
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                long newDate = cal.getDate();
                saveTimeSlots(lastDate, timeSlots);
                // create/load timeslots for day
                if (savedSlots.containsKey(newDate) ){
                    timeSlots = savedSlots.get(newDate);
                } else {
                    timeSlots = new AvailabilityEnum[24];
                }
                lastDate = newDate;

            }
        });
    }

    public void openCalendar(View v){

        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, System.currentTimeMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);
    }

    public void setSlotAvailablity(int index, AvailabilityEnum avail){
        timeSlots[index]=avail;
    }

    //TODO: method(s) to set slot availability from UI

    private void saveTimeSlots(long date, AvailabilityEnum[] times){
        // TODO: Backup to db
        // times is an array of 24 slots (1 hour each), where true means available
        // date is a time since the epoch, in milliseconds
        savedSlots.put(date, times);

    }
}
