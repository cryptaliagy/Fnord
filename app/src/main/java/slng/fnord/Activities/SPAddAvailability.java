package slng.fnord.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import slng.fnord.Helpers.Common;
import slng.fnord.Helpers.Pair;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class SPAddAvailability extends AppCompatActivity {
    ServiceProvider serviceProvider = (ServiceProvider) SignInActivity.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spadd_availability);

        EditText mondayStart = (EditText) findViewById(R.id.mondayStartTime);
        EditText mondayEnd = (EditText) findViewById(R.id.mondayEndTime);

        EditText tuesdayStart = (EditText) findViewById(R.id.tuesdayStartTime);
        EditText tuesdayEnd = (EditText) findViewById(R.id.tuesdayEndTime);

        EditText wednesdayStart = (EditText) findViewById(R.id.wednesdayStartTime);
        EditText wednesdayEnd = (EditText) findViewById(R.id.wednesdayEndTime);

        EditText thursdayStart = (EditText) findViewById(R.id.thursdayStartTime);
        EditText thursdayEnd = (EditText) findViewById(R.id.thursdayEndTime);

        EditText fridayStart = (EditText) findViewById(R.id.fridayStartTime);
        EditText fridayEnd = (EditText) findViewById(R.id.fridayEndTime);

        EditText saturdayStart = (EditText) findViewById(R.id.saturdayStartTime);
        EditText saturdayEnd = (EditText) findViewById(R.id.saturdayEndTime);

        EditText sundayStart = (EditText) findViewById(R.id.sundayStartTime);
        EditText sundayEnd = (EditText) findViewById(R.id.sundayEndTime);

        mondayStart.setText(serviceProvider.getDayAvailability("Monday").first);
        mondayEnd.setText(serviceProvider.getDayAvailability("Monday").second);

        tuesdayStart.setText(serviceProvider.getDayAvailability("Tuesday").first);
        tuesdayEnd.setText(serviceProvider.getDayAvailability("Tuesday").second);

        wednesdayStart.setText(serviceProvider.getDayAvailability("Wednesday").first);
        wednesdayEnd.setText(serviceProvider.getDayAvailability("Wednesday").second);

        thursdayStart.setText(serviceProvider.getDayAvailability("Thursday").first);
        thursdayEnd.setText(serviceProvider.getDayAvailability("Thursday").second);

        fridayStart.setText(serviceProvider.getDayAvailability("Friday").first);
        fridayEnd.setText(serviceProvider.getDayAvailability("Friday").second);

        saturdayStart.setText(serviceProvider.getDayAvailability("Saturday").first);
        saturdayEnd.setText(serviceProvider.getDayAvailability("Saturday").second);

        sundayStart.setText(serviceProvider.getDayAvailability("Sunday").first);
        sundayEnd.setText(serviceProvider.getDayAvailability("Sunday").second);


    }

    public void onClick(View view) {
        EditText mondayStart = (EditText) findViewById(R.id.mondayStartTime);
        EditText mondayEnd = (EditText) findViewById(R.id.mondayEndTime);

        EditText tuesdayStart = (EditText) findViewById(R.id.tuesdayStartTime);
        EditText tuesdayEnd = (EditText) findViewById(R.id.tuesdayEndTime);

        EditText wednesdayStart = (EditText) findViewById(R.id.wednesdayStartTime);
        EditText wednesdayEnd = (EditText) findViewById(R.id.wednesdayEndTime);

        EditText thursdayStart = (EditText) findViewById(R.id.thursdayStartTime);
        EditText thursdayEnd = (EditText) findViewById(R.id.thursdayEndTime);

        EditText fridayStart = (EditText) findViewById(R.id.fridayStartTime);
        EditText fridayEnd = (EditText) findViewById(R.id.fridayEndTime);

        EditText saturdayStart = (EditText) findViewById(R.id.saturdayStartTime);
        EditText saturdayEnd = (EditText) findViewById(R.id.saturdayEndTime);

        EditText sundayStart = (EditText) findViewById(R.id.sundayStartTime);
        EditText sundayEnd = (EditText) findViewById(R.id.sundayEndTime);


        String mondayStartText = mondayStart.getText().toString();
        String mondayEndText = mondayEnd.getText().toString();

        String tuesdayStartText = tuesdayStart.getText().toString();
        String tuesdayEndText = tuesdayEnd.getText().toString();

        String wednesdayStartText = wednesdayStart.getText().toString();
        String wednesdayEndText = wednesdayEnd.getText().toString();

        String thursdayStartText = thursdayStart.getText().toString();
        String thursdayEndText = thursdayEnd.getText().toString();

        String fridayStartText = fridayStart.getText().toString();
        String fridayEndText = fridayEnd.getText().toString();

        String saturdayStartText = saturdayStart.getText().toString();
        String saturdayEndText = saturdayEnd.getText().toString();

        String sundayStartText = sundayStart.getText().toString();
        String sundayEndText = sundayEnd.getText().toString();

        if (!Common.validateTime(mondayStartText) ||
                !Common.validateTime(mondayEndText) ||
                !Common.validateTime(tuesdayStartText) ||
                !Common.validateTime(tuesdayEndText) ||
                !Common.validateTime(wednesdayStartText) ||
                !Common.validateTime(wednesdayEndText) ||
                !Common.validateTime(thursdayStartText) ||
                !Common.validateTime(thursdayEndText) ||
                !Common.validateTime(fridayStartText) ||
                !Common.validateTime(fridayEndText) ||
                !Common.validateTime(saturdayStartText) ||
                !Common.validateTime(saturdayEndText) ||
                !Common.validateTime(sundayStartText) ||
                !Common.validateTime(sundayEndText)) {
            Toast.makeText(getApplicationContext(), "Invalid availability", Toast.LENGTH_LONG).show();
            return;
        }

        HashMap<String, Pair<String, String>> availability = new HashMap<>();

        availability.put("Monday", new Pair<>(mondayStartText, mondayEndText));
        availability.put("Tuesday", new Pair<>(tuesdayStartText, tuesdayEndText));
        availability.put("Wednesday", new Pair<>(wednesdayStartText, wednesdayEndText));
        availability.put("Thursday", new Pair<>(thursdayStartText, thursdayEndText));
        availability.put("Friday", new Pair<>(fridayStartText, fridayEndText));
        availability.put("Saturday", new Pair<>(saturdayStartText, saturdayEndText));
        availability.put("Sunday", new Pair<>(sundayStartText, sundayEndText));

        serviceProvider.setAvailability(availability);
        Toast.makeText(getApplicationContext(), "Availability updated!", Toast.LENGTH_SHORT).show();

    }
}
