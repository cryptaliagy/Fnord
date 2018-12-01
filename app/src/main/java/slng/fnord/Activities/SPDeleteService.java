package slng.fnord.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import slng.fnord.Database.DBHelper;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class SPDeleteService extends AppCompatActivity {
    private Spinner removeServicesSpinner;
    private Button removeService;
    public static String currentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spdelete_service);
        initializeSpinner();

        removeService = findViewById(R.id.SPRemoveServiceButton);

        removeService.setOnClickListener(view -> {
            currentService = removeServicesSpinner.getSelectedItem().toString();

            if (((ServiceProvider) SignInActivity.currentUser).getServiceList().contains(currentService)) {
                ((ServiceProvider) SignInActivity.currentUser).removeService(currentService);
                MainActivity.getServices().getService(currentService)
                        .deleteProvider(((ServiceProvider) SignInActivity.currentUser).getCompany());
                DBHelper.updateServices(MainActivity.getServices());
                DBHelper.updateUser(SignInActivity.currentUser);
                Toast.makeText(getApplicationContext(), "Service removed", Toast.LENGTH_SHORT).show();
                DBHelper.updateUser(SignInActivity.currentUser);
                initializeSpinner();
            } else {
                Toast.makeText(getApplicationContext(), "Error Removing Service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeSpinner() {
        ArrayList<String> services = (ArrayList<String>) ((ServiceProvider) SignInActivity.currentUser).getServiceList();
        removeServicesSpinner = findViewById(R.id.removeServiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        removeServicesSpinner.setAdapter(adapter);

    }


}
