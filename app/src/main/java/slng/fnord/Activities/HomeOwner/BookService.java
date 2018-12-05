package slng.fnord.Activities.HomeOwner;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Activities.Shared.Welcome;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.BookingManager;
import slng.fnord.R;
import slng.fnord.Structures.HomeOwner;
import slng.fnord.Structures.ServiceProvider;

public class BookService extends AppCompatActivity {
    public static ServiceProvider serviceProvider;
    public static String serviceName;
    private Calendar date = Calendar.getInstance();
    private int startTime = -1;
    private int endTime = -1;
    private BookingManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobook_service);

        EditText startTimeTV = findViewById(R.id.pickStartTimeTV);
        EditText endTimeTV = findViewById(R.id.pickEndTimeTV);
        EditText datePickerTV = findViewById(R.id.datePickTV);

        Button confirmButton = findViewById(R.id.confirmBookingButton);
        Button backButton = findViewById(R.id.backConfirmBookingButton);

        HomeOwner user = (HomeOwner) Welcome.currentUser;
        manager = new BookingManager(new DBHelper());

        TextView spCompanyTV = findViewById(R.id.spCompanyTextView);
        TextView serviceNameTV = findViewById(R.id.serviceNameBookingTextView);

        spCompanyTV.setText(serviceProvider.getCompany());
        serviceNameTV.setText(serviceName);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(((view, year, monthOfYear, dayOfMonth) -> {
            date.set(year, monthOfYear, dayOfMonth);
            datePickerTV.setText(sdf.format(date.getTime()).toString());
        }), Calendar.getInstance());

        TimePickerDialog startTimePickerDialog = TimePickerDialog.newInstance(((view, hourOfDay, minute, second) -> {
            startTime = hourOfDay;
            startTimeTV.setText(startTime + ":00");

        }), 0, 0, 0, true);

        TimePickerDialog endTimePickerDialog = TimePickerDialog.newInstance(((view, hourOfDay, minute, second) -> {
            endTime = hourOfDay;
            endTimeTV.setText(endTime+":00");
        }), 23, 0, 0, true);

        startTimeTV.setOnClickListener(view -> {
            if (endTime > 0) {
                startTimePickerDialog.setMaxTime(endTime, 0, 0);
            }
            startTimePickerDialog.show(getSupportFragmentManager(), "Pick a starting time");
        });

        endTimeTV.setOnClickListener(view -> {
            if (startTime > 0) {
                endTimePickerDialog.setMinTime(startTime, 0, 0);
            }

            endTimePickerDialog.show(getSupportFragmentManager(), "Pick an ending time");
        });

        datePickerTV.setOnClickListener(view -> {
            datePickerDialog.setMinDate(Calendar.getInstance());
            datePickerDialog.show(getSupportFragmentManager(), "Pick a date");
        });

        confirmButton.setOnClickListener(view -> {
            if (user == null) {
                System.out.println("null user");
                return;
            }
            manager.makeBooking(user, serviceProvider, serviceName, date, startTime, endTime);
            Toast.makeText(getApplicationContext(),
                    "Booking completed!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Welcome.class));
        });

        backButton.setOnClickListener(view -> {
            serviceName = "";
            serviceProvider = null;
            startActivity(new Intent(this, SearchResults.class));
        });
    }
}
