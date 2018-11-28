package slng.fnord.Activities;

import android.support.v7.app.AppCompatActivity;
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

        removeService = (Button) findViewById(R.id.SPRemoveServiceButton);

         removeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            currentService = removeServicesSpinner.getSelectedItem().toString();

            if (((ServiceProvider) SignInActivity.currentUser).getServiceList().contains(currentService)) {
                ((ServiceProvider) SignInActivity.currentUser).removeService(currentService);
                DBHelper.updateUser(SignInActivity.currentUser);
                Toast toast = Toast.makeText(getApplicationContext(), "Service removed", Toast.LENGTH_SHORT);
                toast.show();
                DBHelper.updateUser(SignInActivity.currentUser);
                initializeSpinner();
            }

            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Error Removing Service", Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        });


    }

    private void initializeSpinner(){
        ArrayList<String> services = (ArrayList<String>) ((ServiceProvider) SignInActivity.currentUser).getServiceList();
        removeServicesSpinner = (Spinner) findViewById(R.id.removeServiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        removeServicesSpinner.setAdapter(adapter);

    }


}
