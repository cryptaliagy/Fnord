package slng.fnord;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteService extends AppCompatActivity {
    private Spinner deleteServicesSpinner;
    private Button deleteService;
    String serviceToDelete;
    private Services services = MainActivity.getServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service);

        initializeSpinner(services.asArrayList());
        deleteService = (Button) findViewById(R.id.deleteServiceBtn);
        deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting the text of the selected service in the list, then deleting it and the rate associated with it
                if(services.size() != 0) {
                    serviceToDelete = deleteServicesSpinner.getSelectedItem().toString();
                    services.deleteService(serviceToDelete);
                    DBHelper.deleteService(serviceToDelete);
                    Toast toastDelete = Toast.makeText(getApplicationContext(), "Service Deleted.", Toast.LENGTH_SHORT);
                    toastDelete.show();
                    initializeSpinner(services.asArrayList());
                }
                else{
                    Toast toastNoDelete = Toast.makeText(getApplicationContext(), "No Services Selected.", Toast.LENGTH_SHORT);
                    toastNoDelete.show();
                }
            }
        });
    }

    //helper function that creates/initializes the spinner aka the dropdown list
    private void initializeSpinner(ArrayList<String> services){
        deleteServicesSpinner = (Spinner) findViewById(R.id.servicesSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteServicesSpinner.setAdapter(adapter);
    }



}
