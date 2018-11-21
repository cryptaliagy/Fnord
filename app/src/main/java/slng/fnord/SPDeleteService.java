package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SPDeleteService extends AppCompatActivity {

    private Spinner removeServicesSpinner;
    private Button removeService;
    public static ArrayList services = (ArrayList) ((ServiceProvider) SignInActivity.currentUser).getServiceList();
    public static String currentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spdelete_service);
        //initializeSpinner();

        /**
         deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //setting the text of the selected service in the list, then deleting it and the rate associated with it

            Toast toastDelete = Toast.makeText(getApplicationContext(), "Service Removed.", Toast.LENGTH_SHORT);
            toastDelete.show();
            initializeSpinner(services.asArrayList());
            }

            Toast toastNoDelete = Toast.makeText(getApplicationContext(), "No Services Selected.", Toast.LENGTH_SHORT);
            toastNoDelete.show();
        }

        });

        **/
    }

    /**private void initializeSpinner(){
        addServicesSpinner = (Spinner) findViewById(R.id.removeServiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addServicesSpinner.setAdapter(adapter);

    }**/


}
