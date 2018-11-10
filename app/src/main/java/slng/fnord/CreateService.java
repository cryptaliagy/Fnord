package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateService extends AppCompatActivity {
    private Button createService;
    String serviceToAdd;
    String rateOfService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        createService = (Button) findViewById(R.id.createServiceButton);
        createService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editable field to specify a service
                EditText serviceToAddText = (EditText) findViewById(R.id.serviceToAddField);
                serviceToAdd = serviceToAddText.getText().toString();

                //editable field to specify rate of the service
                EditText rateOfServiceText = (EditText) findViewById(R.id.ratePerHourField);
                rateOfService = rateOfServiceText.getText().toString();

                //if the static arraylist of services doesn't have the service we are trying to add, add the service + its rate
                if(MainActivity.getServices().getServices().contains(serviceToAdd)==false){
                    MainActivity.getServices().addService(serviceToAdd);
                    MainActivity.getServices().addServiceRate(rateOfService);
                    Toast toastCreate = Toast.makeText(getApplicationContext(), "Service Created.", Toast.LENGTH_SHORT);
                    toastCreate.show();

                }
                else{
                    Toast toastNoCreate = Toast.makeText(getApplicationContext(), "No Service Created. Service Already Exists.", Toast.LENGTH_SHORT);
                    toastNoCreate.show();
                }
                //can probably add another else statement here and have it show a toast showing that the service already exists and thus
                //cannot be added
                //TODO need some verifications here/authentication, namely, checking if the service exists, checking if its a valid service (i.e. not a blank line)
                //and of course, the appropriate toast
            }
        });

    }
}
