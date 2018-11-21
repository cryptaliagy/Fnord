package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SPAddService extends AppCompatActivity {
    private Spinner addServicesSpinner;
    private Button addService;
    public static Services ser = MainActivity.getServices();
    public static String currentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spadd_service);

        initializeSpinner();

        addService = (Button) findViewById(R.id.SPAddServiceButton);

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentService = addServicesSpinner.getSelectedItem().toString();

                if (((ServiceProvider) SignInActivity.currentUser).getServiceList().contains(currentService)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Service has already been added", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                else {
                    currentService = addServicesSpinner.getSelectedItem().toString();
                    ((ServiceProvider) SignInActivity.currentUser).addService(currentService);
                    Toast toast = Toast.makeText(getApplicationContext(), "Service has been added", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
            }
        });

    }


    private void initializeSpinner(){
        addServicesSpinner = (Spinner) findViewById(R.id.addServiceSpinner);
        ArrayList<String> services = ser.asArrayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addServicesSpinner.setAdapter(adapter);

    }
}
