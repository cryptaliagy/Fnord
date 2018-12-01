package slng.fnord.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import slng.fnord.Database.DBHelper;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.Services;

public class SPAddService extends AppCompatActivity {
    private Spinner addServicesSpinner;
    private Button addService;
    private static Services ser = MainActivity.getServices();
    private static String currentService;
    private static Boolean certified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spadd_service);

        Switch certifiedSwitch = findViewById(R.id.switch1);

        certifiedSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> certified = isChecked));

        initializeSpinner();

        addService = findViewById(R.id.SPAddServiceButton);

        addService.setOnClickListener(view -> {
            currentService = addServicesSpinner.getSelectedItem().toString();

            if (((ServiceProvider) SignInActivity.currentUser).getServiceList().contains(currentService)) {
                Toast.makeText(getApplicationContext(), "Service has already been added", Toast.LENGTH_SHORT).show();
            } else {
                currentService = addServicesSpinner.getSelectedItem().toString();

                ((ServiceProvider) SignInActivity.currentUser).addService(currentService, certified);
                DBHelper.updateUser(SignInActivity.currentUser);
                Toast.makeText(getApplicationContext(), "Service has been added", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.backSPAddService).setOnClickListener(view -> startActivity(new Intent(this, SPViewService.class)));

    }


    private void initializeSpinner() {
        addServicesSpinner = findViewById(R.id.addServiceSpinner);
        ArrayList<String> services = ser.asArrayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addServicesSpinner.setAdapter(adapter);

    }
}
