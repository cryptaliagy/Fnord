package slng.fnord.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBHelper;
import slng.fnord.R;
import slng.fnord.Structures.Services;

public class EditService extends AppCompatActivity {
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services2);

        //two textviews for the editable service and service rate
        final TextView serviceView = findViewById(R.id.serviceNameEditField);
        serviceView.setText(EditServicesSelect.currentService);

        final TextView rateView = findViewById(R.id.serviceRateEditField);
        rateView.setText(EditServicesSelect.currentServiceRate);

        confirm = findViewById(R.id.confirmChangesBtn);
        confirm.setOnClickListener(view -> {
            //will need to make some validations on the new service name and rate before we actually add it
            //i.e. if the new service we are trying to add is blank or if it already exists, we make a toast saying it was not added (and ofc it wasnt added)

            if (serviceView.getText().toString().isEmpty() || rateView.getText().toString().isEmpty()) {
                Toast toastBlank = Toast.makeText(getApplicationContext(), "That Field Cannot Be Blank.", Toast.LENGTH_SHORT);
                toastBlank.show();
                return;

            }

            String newServiceName = serviceView.getText().toString();
            String prevServiceName = EditServicesSelect.currentService;
            String rate = rateView.getText().toString();
            Services ser = MainActivity.getServices();


            if (!Common.validateService(newServiceName)) {
                Toast toastInvalidServiceName = Toast.makeText(getApplicationContext(),
                        "The service name is invalid", Toast.LENGTH_SHORT);
                toastInvalidServiceName.show();
                return;
            }

            if (!Common.validatePrice(rate)) {
                Toast toastInvalidRate = Toast.makeText(getApplicationContext(),
                        "The service rate is invalid", Toast.LENGTH_SHORT);
                toastInvalidRate.show();
                return;
            }

            if (!newServiceName.equals(prevServiceName)) {
                if (ser.hasService(newServiceName)) {
                    Toast toastNoEdit = Toast.makeText(getApplicationContext(), "A Service With That Name Already Exists. Please Choose Another Name.", Toast.LENGTH_SHORT);
                    toastNoEdit.show();
                    return;
                }
                ser.deleteService(prevServiceName);
                DBHelper.deleteService(prevServiceName);
            }

            ser.addService(newServiceName, Double.valueOf(rate));
            DBHelper.updateServices(ser);
            Toast toastEdit = Toast.makeText(getApplicationContext(), "Service Edited.", Toast.LENGTH_SHORT);
            toastEdit.show();
        });

    }
}
