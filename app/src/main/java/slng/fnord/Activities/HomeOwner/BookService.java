package slng.fnord.Activities.HomeOwner;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class BookService extends AppCompatActivity {
    public static ServiceProvider serviceProvider;
    public static String serviceName;
    private Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobook_service);

        EditText startTimeTV = findViewById(R.id.pickStartTimeTV);
        EditText endTimeTV = findViewById(R.id.pickEndTimeTV);

        EditText datePickerTV = findViewById(R.id.datePickTV);


        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(((view, year, monthOfYear, dayOfMonth) -> {
            date.set(year, monthOfYear, dayOfMonth);
            datePickerTV.setText(date.toString());
        }), Calendar.getInstance());

        TimePickerDialog startTimePickerDialog = TimePickerDialog.newInstance(((view, hourOfDay, minute, second) -> {

        }), 0, 0, 0, true);

        TimePickerDialog endTimePickerDialog = TimePickerDialog.newInstance(((view, hourOfDay, minute, second) -> {
            
        }), 23, 0, 0, true);

    }
}
