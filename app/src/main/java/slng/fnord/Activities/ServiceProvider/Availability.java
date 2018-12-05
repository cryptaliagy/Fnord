package slng.fnord.Activities.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.HashMap;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Activities.Shared.Welcome;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Pair;
import slng.fnord.Managers.AccountManager;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class Availability extends AppCompatActivity {
    ServiceProvider serviceProvider = (ServiceProvider) Welcome.currentUser;
    HashMap<String, Pair<Integer, Integer>> currentAvailability = serviceProvider.getAvailability();
    HashMap<String, Pair<Integer, Integer>> newAvailability = new HashMap<>();
    AccountManager manager = new AccountManager(new DBHelper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spadd_availability);

        for (String key : currentAvailability.keySet()) {
            newAvailability.put(key, currentAvailability.get(key).clone());
        }

        EditText mondayStartTimeTV = findViewById(R.id.mondayStartTime);
        EditText mondayEndTimeTV = findViewById(R.id.mondayEndTime);

        EditText tuesdayStartTimeTV = findViewById(R.id.tuesdayStartTime);
        EditText tuesdayEndTimeTV = findViewById(R.id.tuesdayEndTime);

        EditText wednesdayStartTimeTV = findViewById(R.id.wednesdayStartTime);
        EditText wednesdayEndTimeTV = findViewById(R.id.wednesdayEndTime);

        EditText thursdayStartTimeTV = findViewById(R.id.thursdayStartTime);
        EditText thursdayEndTimeTV = findViewById(R.id.thursdayEndTime);

        EditText fridayStartTimeTV = findViewById(R.id.fridayStartTime);
        EditText fridayEndTimeTV = findViewById(R.id.fridayEndTime);

        EditText saturdayStartTimeTV = findViewById(R.id.saturdayStartTime);
        EditText saturdayEndTimeTV = findViewById(R.id.saturdayEndTime);

        EditText sundayStartTimeTV = findViewById(R.id.sundayStartTime);
        EditText sundayEndTimeTV = findViewById(R.id.sundayEndTime);

        createTimePickerDialog(mondayStartTimeTV,      mondayEndTimeTV,       "Monday");
        createTimePickerDialog(tuesdayStartTimeTV,     tuesdayEndTimeTV,      "Tuesday");
        createTimePickerDialog(wednesdayStartTimeTV,   wednesdayEndTimeTV,    "Wednesday");
        createTimePickerDialog(thursdayStartTimeTV,    thursdayEndTimeTV,     "Thursday");
        createTimePickerDialog(fridayStartTimeTV,      fridayEndTimeTV,       "Friday");
        createTimePickerDialog(saturdayStartTimeTV,    saturdayEndTimeTV,     "Saturday");
        createTimePickerDialog(sundayStartTimeTV,      sundayEndTimeTV,       "Sunday");

        Button updateButton = findViewById(R.id.updateAvailibility);

        updateButton.setOnClickListener(view -> {
            serviceProvider.setAvailability(newAvailability);
            manager.updateUser(serviceProvider);
            Toast.makeText(getApplicationContext(), "Availability updated!", Toast.LENGTH_SHORT).show();
        });
    }

    public void createTimePickerDialog(EditText startTime, EditText endTime, String day) {
        TimePickerDialog startTimeDialog;
        TimePickerDialog endTimeDialog;

        Pair<Integer, Integer> dayAvailability = newAvailability.get(day).clone();

        if (dayAvailability.getFirst() > 0) {
            startTime.setText(dayAvailability.getFirst() + ":00");
        }

        if (dayAvailability.getSecond() > 0) {
            endTime.setText(dayAvailability.getSecond() + ":00");
        }


        startTimeDialog = TimePickerDialog.newInstance(
                ((view, hourOfDay, minute, second) -> {
                    dayAvailability.setFirst(hourOfDay);
                    startTime.setText(dayAvailability.getFirst() + ":00");
                    newAvailability.put(day, dayAvailability);
                }),
                0, 0, 0, true);

        endTimeDialog = TimePickerDialog.newInstance(
                ((view, hourOfDay, minute, second) -> {
                    dayAvailability.setSecond(hourOfDay);
                    endTime.setText(dayAvailability.getSecond() + ":00");
                    newAvailability.put(day, dayAvailability);
                }),
                23, 0, 0, true);

        startTimeDialog.enableMinutes(false);
        startTimeDialog.enableSeconds(false);

        endTimeDialog.enableMinutes(false);
        endTimeDialog.enableSeconds(false);

        startTime.setOnClickListener(view -> {
            int currentEndTime = dayAvailability.getSecond();

            if (currentEndTime > 0) {
                startTimeDialog.setMaxTime(currentEndTime, 0, 0);
            }

            startTimeDialog.show(getSupportFragmentManager(), "Select start time");
        });

        endTime.setOnClickListener( view -> {
            int currentStartTime = dayAvailability.getFirst();

            if (currentStartTime > 0) {
                endTimeDialog.setMinTime(currentStartTime, 0, 0);
            }

            endTimeDialog.show(getSupportFragmentManager(), "Select end time");
        });
    }
}
