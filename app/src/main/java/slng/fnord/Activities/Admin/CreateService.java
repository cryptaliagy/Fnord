package slng.fnord.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import io.reactivex.Observable;
import io.reactivex.Observer;
import slng.fnord.Activities.Shared.MainActivity;
import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.Services;

public class CreateService extends AppCompatActivity {
    private Button createService;
    String serviceToAdd;
    String rateOfService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        ServicesManager manager = new ServicesManager(new DBHelper());

        createService = findViewById(R.id.createServiceButton);
        createService.setOnClickListener(view -> {
            //editable field to specify a service
            EditText serviceToAddText = findViewById(R.id.serviceToAddField);
            serviceToAdd = serviceToAddText.getText().toString();

            //editable field to specify rate of the service
            EditText rateOfServiceText = findViewById(R.id.ratePerHourField);
            rateOfService = rateOfServiceText.getText().toString();

            if (!Common.validateService(serviceToAdd)) {
                Toast.makeText(getApplicationContext(), "Service name invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Common.validatePrice(rateOfService)) {
                Toast.makeText(getApplicationContext(),
                        "Service price invalid, must be a number with at most 2 decimal places", Toast.LENGTH_SHORT).show();
                return;
            }

            manager.makeService(serviceToAdd, Double.valueOf(rateOfService), this::creationCallback);
        });

    }

    public void creationCallback(Service service) {
        if (service == null) {
            Toast.makeText(getApplicationContext(), "A service with that name already exists",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Service created!", Toast.LENGTH_SHORT).show();
        }

    }
}
