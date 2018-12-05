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
import java.util.Optional;

import slng.fnord.Activities.Shared.MainActivity;
import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.AccountManager;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.Services;

public class AddService extends AppCompatActivity {
    private Spinner addServicesSpinner;
    private Button addService;
    private static String currentService;
    private static Boolean certified = false;
    private ServicesManager serviceManager;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spadd_service);

        serviceManager = new ServicesManager(new DBHelper());
        accountManager = new AccountManager(new DBHelper());

        Switch certifiedSwitch = findViewById(R.id.switch1);

        certifiedSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> certified = isChecked));

        serviceManager.getServiceNamesArrayList(this::initializeSpinner);

        addService = findViewById(R.id.SPAddServiceButton);

        addService.setOnClickListener(view -> {
            currentService = addServicesSpinner.getSelectedItem().toString();
            ServiceProvider provider = (ServiceProvider) SignInActivity.currentUser;

            if (provider.getServiceList().contains(currentService)) {
                Toast.makeText(getApplicationContext(), "Service has already been added", Toast.LENGTH_SHORT).show();
            } else {
                currentService = addServicesSpinner.getSelectedItem().toString();

                provider.addService(currentService, certified);
                accountManager.updateUser(provider);
                serviceManager.getService(currentService, this::serviceCallback);
                Toast.makeText(getApplicationContext(), "Service has been added", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.backSPAddService).setOnClickListener(view -> startActivity(new Intent(this, ViewServices.class)));

    }

    public void serviceCallback(Service service) {
        if (service != null) {
            service.addProvider((ServiceProvider) SignInActivity.currentUser);
            serviceManager.updateService(service);
        }
    }


    private void initializeSpinner(ArrayList<String> services) {
        if (services == null) {
            return;
        }

        addServicesSpinner = findViewById(R.id.addServiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addServicesSpinner.setAdapter(adapter);

    }
}
