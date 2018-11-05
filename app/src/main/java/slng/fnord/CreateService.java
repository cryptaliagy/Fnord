package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                if(MainActivity.ser.getServices().contains(serviceToAdd)==false){
                    MainActivity.ser.addService(serviceToAdd);
                    MainActivity.ser.addServiceRate(rateOfService);
                    //TODO a toast here showing the service has been added would be nice
                    //at this point, make a popup/toast/warning to show that the service has been added
                }
                //can probably add another else statement here and have it show a toast showing that the service already exists and thus
                //cannot be added
                //TODO need some verifications here/authentication, namely, checking if the service exists, checking if its a valid service (i.e. not a blank line)
                //and of course, the appropriate toast
            }
        });

    }
}
