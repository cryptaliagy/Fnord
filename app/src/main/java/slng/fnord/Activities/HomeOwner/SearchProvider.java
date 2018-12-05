package slng.fnord.Activities.HomeOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Pair;
import slng.fnord.Managers.AccountManager;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.ServiceProviderMeta;
import slng.fnord.Structures.User;

public class SearchProvider extends AppCompatActivity {
    public static Service serviceRequested;
    private static String serviceRequestedString;
    private static HashMap<String, ServiceProviderMeta> goodProviders;
    private Spinner searchServiceSpinner;
    private Button spSearchButton;
    private ServicesManager managerSer;
    private HashMap<String, ServiceProviderMeta> serviceProviders; //company name is key
    private Calendar date = null;
    private EditText edittext;
    private int startTime = -1;
    private int endTime = -1;
    private float minRating = 0;
    public RatingBar ratingBar;
    private ServiceProvider provider;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosearch_sp);

        goodProviders = new HashMap<>();

        //manager for getting service info
        managerSer = new ServicesManager(new DBHelper());
        //manager gets service names and initializes the spinner with them
        managerSer.getServiceNamesArrayList(this::initializeSpinner);

        //Find text box for date, and use a date picker dialog to select a date which is saved in a
        //Calendar type date
        edittext = findViewById(R.id.Date);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(((view, year, monthOfYear, dayOfMonth) -> {
            if (date == null) {
                date = Calendar.getInstance();
            }
            date.set(year, monthOfYear, dayOfMonth);
            updateLabel();
        }), Calendar.getInstance());




        edittext.setOnClickListener(view -> {
            datePickerDialog.setMinDate(Calendar.getInstance());
            datePickerDialog.show(getSupportFragmentManager(), "Select a date");
        });

        //Find text box for time, and use a time picker dialog to select a date which is saved in a
        //tine type time
        EditText startTimeEditText = findViewById(R.id.startTimeEditText);

        TimePickerDialog startTimePickerDialog = TimePickerDialog.newInstance(((view, hourOfDay, minute, second) -> {
            startTime = hourOfDay;
            startTimeEditText.setText(startTime + ":00");

        }), 0, 0, 0, true);

        startTimeEditText.setOnClickListener(view -> {
            if (endTime > -1) {
                startTimePickerDialog.setMaxTime(endTime, 0, 0);
            }
            startTimePickerDialog.show(getSupportFragmentManager(), "Pick a start time");
        });

        startTimePickerDialog.enableMinutes(false);
        startTimePickerDialog.enableSeconds(false);

        EditText endTimeEditText = findViewById(R.id.endTimeEditText);

        TimePickerDialog endTimePickerDialog = TimePickerDialog.newInstance(((view, hourOfDay, minute, second) -> {
            endTime = hourOfDay;
            endTimeEditText.setText(endTime + ":00");
        }), 23, 0, 0, true);

        endTimePickerDialog.enableMinutes(false);
        endTimePickerDialog.enableSeconds(false);

        endTimeEditText.setOnClickListener(view -> {
            if (startTime > -1) {
                endTimePickerDialog.setMinTime(startTime, 0, 0);
            }
            endTimePickerDialog.show(getSupportFragmentManager(), "Pick an end time");
        });

        //find rating bar for later use
        ratingBar = findViewById(R.id.ratingBar);

        //find search button
        spSearchButton = findViewById(R.id.spSearchButton);


        spSearchButton.setOnClickListener(view -> {
            //get name of service from spinner
            serviceRequestedString = searchServiceSpinner.getSelectedItem().toString();
            //get rating from ratingBar
            minRating = ratingBar.getRating();
            managerSer.getService(serviceRequestedString, this::handleService);//using a manager, gets the service item and saves it to ServiceRequested
        });
    }

    private void handleService(Service serviceOptional) {
        if (serviceOptional == null) {
            serviceRequested = null;
            Toast toastNoResults = Toast.makeText(getApplicationContext(), "service sent to setServiceRequested is null", Toast.LENGTH_SHORT); // should not be possible
            toastNoResults.show();
        } else {
            serviceRequested = serviceOptional;

            HashMap<String, ServiceProviderMeta> providers = serviceRequested.getProviders();
            goodProviders = new HashMap<>();

            if (date == null) {
                edittext.setError("Please select a day");
                return;
            }

            if (startTime == -1) {
                ((EditText) findViewById(R.id.startTimeEditText)).setError("Please select a start time");
                return;
            }

            if (endTime == -1) {
                ((EditText) findViewById(R.id.endTimeEditText)).setError("Please select an end time");
                return;
            }

            // All search criteria is filled

            for (String key : providers.keySet()) {
                int dayNumber = date.get(Calendar.DAY_OF_WEEK);
                String day = "";
                switch (dayNumber) {
                    case 1:
                        day = "Sunday";
                        break;
                    case 2:
                        day = "Monday";
                        break;
                    case 3:
                        day = "Tuesday";
                        break;
                    case 4:
                        day = "Wednesday";
                        break;
                    case 5:
                        day = "Thursday";
                        break;
                    case 6:
                        day = "Friday";
                        break;
                    case 7:
                        day = "Saturday";
                        break;
                }
                ServiceProviderMeta providerMeta = providers.get(key);
                int availableStart;
                int availableEnd;
                Pair<Integer, Integer> availability = providerMeta.getAvailabilities().get(day);

                availableStart = availability.getFirst();
                availableEnd = availability.getSecond();

                if (availableStart <= startTime &&
                        availableEnd >= endTime &&
                        providerMeta.getAverageRating() >= minRating) {
                    goodProviders.put(key, providerMeta);
                }
            }

            openHOSearchResults();
        }

    }


    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(date.getTime()));
    }


    public void openHOSearchResults() {
        Intent intent = new Intent(this, SearchResults.class);
        startActivity(intent);
    }

    private void initializeSpinner(ArrayList<String> servicesOptional) {
        ArrayList<String> services;
        if (servicesOptional == null) {
            services = new ArrayList<>();
        } else {
            services = servicesOptional;
        }
        searchServiceSpinner = findViewById(R.id.searchServiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchServiceSpinner.setAdapter(adapter);
    }


    public static HashMap<String, ServiceProviderMeta> getGoodProviders() {
        return goodProviders;
    }
}






















