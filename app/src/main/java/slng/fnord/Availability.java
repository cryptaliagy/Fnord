package slng.fnord;

import android.view.View;

import java.util.HashMap;

public class Availability {

    private HashMap<WeekDay, AvailabilityEnum[]> savedSlots= new HashMap<WeekDay, AvailabilityEnum[]>();
    private WeekDay[] weekDays = new WeekDay[]{WeekDay.SUNDAY, WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY, WeekDay.THURSDAY, WeekDay.FRIDAY, WeekDay.SATURDAY};

    public Availability() {
        initTimeSlots();
    }

    /*public void openCalendar(View v){

        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, System.currentTimeMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);
    }*/

    public void setSlotAvailablity(int index, WeekDay day, AvailabilityEnum avail){
        AvailabilityEnum[] timeSlots = getTimeSlots(day);
        timeSlots[index]=avail;
        saveTimeSlots(day, timeSlots);
    }

    //TODO: method(s) to set slot availability from UI

    private void saveTimeSlots(WeekDay day, AvailabilityEnum[] times){
        // TODO: Backup to db
        // times is an array of 24 slots (1 hour each), where true means available
        // date is a time since the epoch, in milliseconds
        savedSlots.put(day, times);

    }

    private void initTimeSlots(){
        // init savedSlots; sets all time slots to AVAILABLE
        for (int dayIndex=0; dayIndex<weekDays.length; dayIndex++){
            AvailabilityEnum[] slots = new AvailabilityEnum[24];
            for (int i=0; i<24; i++){
                slots[i]=AvailabilityEnum.AVAILABLE;
            }
            saveTimeSlots(weekDays[dayIndex], slots);
        }
    }

    public AvailabilityEnum[] getTimeSlots(WeekDay day){
        return savedSlots.get(day);
    }

    public void openCalendar(View view) {
        
    }
}
