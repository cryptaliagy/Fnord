package slng.fnord.Activities.HomeOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.security.Provider;
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
import slng.fnord.Structures.Services;
import slng.fnord.Structures.User;

public class SearchProvider extends AppCompatActivity {
    private static Service serviceRequested;
    private static String serviceRequestedString;
    private static HashMap<String, ServiceProviderMeta> goodProviders;
    private Spinner searchServiceSpinner;
    private Button spSearchButton;
    private ServicesManager managerSer;
    private AccountManager managerAcc;
    private HashMap<String, ServiceProviderMeta> serviceProviders; //company name is key
    final Calendar myCalendar = Calendar.getInstance();
    private Calendar timeCalendar = Calendar.getInstance();
    private EditText edittext;
    private int time =-1;
    private float minRating=0;
    public RatingBar ratingBar;
    private ServiceProvider provider;
    private boolean flag = true;

    private android.app.TimePickerDialog timepickerdialog;
    private int CalendarHour, CalendarMinute;


        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hosearch_sp);
            managerSer = new ServicesManager(new DBHelper());

            managerSer.getServiceNamesArrayList(this::initializeSpinner);


            edittext = findViewById(R.id.Date);
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();

                }


            };

            edittext.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(SearchProvider.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });


            EditText timeTV = findViewById(R.id.time);

            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(((view, hourOfDay, minute, second) -> {
                time = hourOfDay;
                timeTV.setText(time + ":00");

            }), 0, 0, 0, true);

            timeTV.setOnClickListener(view -> {
                timePickerDialog.show(getSupportFragmentManager(), "Pick a time");
            });

            ratingBar = findViewById(R.id.ratingBar);

            spSearchButton = findViewById(R.id.spSearchButton);

            spSearchButton.setOnClickListener(view -> {
                serviceRequestedString = searchServiceSpinner.getSelectedItem().toString();
                minRating=ratingBar.getRating();
                managerSer.getService(serviceRequestedString, this::setServiceRequsted);
                goodProviders = new HashMap<>();

                if(goodProviders!=null){matchService();}
                else{
                    Toast toastNoResults = Toast.makeText(getApplicationContext(), "goodProviders Null", Toast.LENGTH_SHORT);
                    toastNoResults.show();
                    flag=false;
                }
                if(flag){matchAvailibility();}
                if(flag){matchMinimumStars();}

                if (goodProviders.size() == 0) {
                    Toast toastNoResults = Toast.makeText(getApplicationContext(), "No results found.", Toast.LENGTH_SHORT);
                    toastNoResults.show();
                } else {
                    if(flag) {
                        openHOSearchResults();
                    }
                }
            });
        }




    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }




        protected void matchService(){
            if (serviceRequested != null) {
                serviceProviders = serviceRequested.getProviders(); //
                
                if (serviceProviders!= null){goodProviders = new HashMap<String, ServiceProviderMeta>(serviceProviders);}

                if (serviceProviders == null){
                    Toast toastNoResults = Toast.makeText(getApplicationContext(), "serviceProviders null", Toast.LENGTH_SHORT);
                    toastNoResults.show();
                }
            }
            else{
                Toast toastNoResults = Toast.makeText(getApplicationContext(), "serviceRequested null", Toast.LENGTH_SHORT);
                toastNoResults.show();
                flag = false;
            }
        }

        protected void matchAvailibility(){
            if(time !=-1) {
                goodProviders = new HashMap<String, ServiceProviderMeta>();
                for (String company : serviceProviders.keySet()) {
                    HashMap<String, Pair<Integer, Integer>> availibilites = serviceProviders.get(company).getAvailabilities();
                    for (String day : availibilites.keySet()) {
                        int daynum = 0;
                        if (day == "Sunday") {
                            daynum = 1;
                        } else if (day == "Monday") {
                            daynum = 2;
                        } else if (day == "Tuesday") {
                            daynum = 3;
                        } else if (day == "Wednesday") {
                            daynum = 4;
                        } else if (day == "Thursday") {
                            daynum = 5;
                        } else if (day == "Friday") {
                            daynum = 6;
                        } else if (day == "Saturday") {
                            daynum = 7;
                        }

                        if (daynum == myCalendar.get(7)) {
                            if ((Integer) availibilites.get(day).getFirst() > time && (Integer) availibilites.get(day).getSecond() < time) {
                                if (!goodProviders.containsKey(company)){
                                    goodProviders.put(company, serviceProviders.get(company));
                                }
                            }
                        }
                    }

                    //availibilites = hashmap <day, pair<starttime, end time>

                }
            }
        }




        protected void matchMinimumStars(){
           for(String company : goodProviders.keySet()){
               managerAcc.getUser(goodProviders.get(company).getEmail(), this::setProvider);
               if (provider.getAverageRating() > minRating){
                   goodProviders.remove(company);
               }
           }

        }

    private void setProvider(User user) {
        provider = (ServiceProvider) user;
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



        private void setServiceRequsted (Service serviceOptional){

            if (serviceOptional == null) {
                serviceRequested = null;
            } else {
                serviceRequested = serviceOptional;
            }
        }

    public static HashMap<String, ServiceProviderMeta> getGoodProviders() {
        return goodProviders;
    }
}






















