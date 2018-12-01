package slng.fnord.Activities.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.HashMap;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Pair;
import slng.fnord.Managers.AccountManager;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class Availability extends AppCompatActivity {
    ServiceProvider serviceProvider = (ServiceProvider) SignInActivity.currentUser;
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

        Button mondayStartTimebtn = findViewById(R.id.mondayStartTime);
        Button mondayEndTimebtn = findViewById(R.id.mondayEndTime);

        Button tuesdayStartTimebtn = findViewById(R.id.tuesdayStartTime);
        Button tuesdayEndTimebtn = findViewById(R.id.tuesdayEndTime);

        Button wednesdayStartTimebtn = findViewById(R.id.wednesdayStartTime);
        Button wednesdayEndTimebtn = findViewById(R.id.wednesdayEndTime);

        Button thursdayStartTimebtn = findViewById(R.id.thursdayStartTime);
        Button thursdayEndTimebtn = findViewById(R.id.thursdayEndTime);

        Button fridayStartTimebtn = findViewById(R.id.fridayStartTime);
        Button fridayEndTimebtn = findViewById(R.id.fridayEndTime);

        Button saturdayStartTimebtn = findViewById(R.id.saturdayStartTime);
        Button saturdayEndTimebtn = findViewById(R.id.saturdayEndTime);

        Button sundayStartTimebtn = findViewById(R.id.sundayStartTime);
        Button sundayEndTimebtn = findViewById(R.id.sundayEndTime);

        createTimePickerDialog(mondayStartTimebtn,      mondayEndTimebtn,       "Monday");
        createTimePickerDialog(tuesdayStartTimebtn,     tuesdayEndTimebtn,      "Tuesday");
        createTimePickerDialog(wednesdayStartTimebtn,   wednesdayEndTimebtn,    "Wednesday");
        createTimePickerDialog(thursdayStartTimebtn,    thursdayEndTimebtn,     "Thursday");
        createTimePickerDialog(fridayStartTimebtn,      fridayEndTimebtn,       "Friday");
        createTimePickerDialog(saturdayStartTimebtn,    saturdayEndTimebtn,     "Saturday");
        createTimePickerDialog(sundayStartTimebtn,      sundayEndTimebtn,       "Sunday");

        Button updateButton = findViewById(R.id.updateAvailibility);

        updateButton.setOnClickListener(view -> {
            serviceProvider.setAvailability(newAvailability);
            manager.updateUser(serviceProvider);
            Toast.makeText(getApplicationContext(), "Availability updated!", Toast.LENGTH_SHORT).show();
        });
    }

    public void createTimePickerDialog(Button startTime, Button endTime, String day) {
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
