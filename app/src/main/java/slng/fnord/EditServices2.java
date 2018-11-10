package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditServices2 extends AppCompatActivity {
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services2);

        //two textviews for the editable service and service rate
        final TextView serviceView = (TextView) findViewById(R.id.serviceNameEditField);
        serviceView.setText(EditServices.currentService);

        final TextView rateView = (TextView) findViewById(R.id.serviceRateEditField);
        rateView.setText(EditServices.currentServiceRate);

        confirm = (Button) findViewById(R.id.confirmChangesBtn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will need to make some validations on the new service name and rate before we actually add it
                //i.e. if the new service we are trying to add is blank or if it already exists, we make a toast saying it was not added (and ofc it wasnt added)

                if (serviceView.getText().toString().isEmpty() || rateView.getText().toString().isEmpty()) {

                    Toast toastBlank = Toast.makeText(getApplicationContext(), "That Field Cannot Be Blank.", Toast.LENGTH_SHORT);
                    toastBlank.show();
                    return;

                }

                String newServiceName = serviceView.getText().toString();
                String prevServiceName = EditServices.currentService;
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
                }

                ser.addService(newServiceName, Double.valueOf(rate));
                Toast toastEdit = Toast.makeText(getApplicationContext(), "Service Edited.", Toast.LENGTH_SHORT);
                toastEdit.show();
            }
        });

    }
}
