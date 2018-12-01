package slng.fnord.Activities.ServiceProvider;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import slng.fnord.Activities.Shared.MainActivity;
import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.Services;

public class AddService extends AppCompatActivity {
    private Spinner addServicesSpinner;
    private Button addService;
    private static String currentService;
    private static Boolean certified = false;
    private ServicesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spadd_service);

        manager = new ServicesManager(new DBHelper());

        Switch certifiedSwitch = findViewById(R.id.switch1);

        certifiedSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> certified = isChecked));

        manager.getServiceNamesArrayList(this::initializeSpinner);

        addService = findViewById(R.id.SPAddServiceButton);

        addService.setOnClickListener(view -> {
            currentService = addServicesSpinner.getSelectedItem().toString();

            if (((ServiceProvider) SignInActivity.currentUser).getServiceList().contains(currentService)) {
                Toast.makeText(getApplicationContext(), "Service has already been added", Toast.LENGTH_SHORT).show();
            } else {
                currentService = addServicesSpinner.getSelectedItem().toString();

                ((ServiceProvider) SignInActivity.currentUser).addService(currentService, certified);
                new DBHelper().updateUser(SignInActivity.currentUser);
                Toast.makeText(getApplicationContext(), "Service has been added", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.backSPAddService).setOnClickListener(view -> startActivity(new Intent(this, ViewServices.class)));

    }


    private void initializeSpinner(ArrayList<String> services) {
        addServicesSpinner = findViewById(R.id.addServiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addServicesSpinner.setAdapter(adapter);

    }
}
