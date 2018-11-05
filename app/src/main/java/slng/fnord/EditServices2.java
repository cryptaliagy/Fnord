package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditServices2 extends AppCompatActivity {
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services2);

        //keeping track of the index of the service selected in the list in the previous screen (EditServices)
        final int indexToChange = MainActivity.ser.getServices().indexOf(EditServices.currentService);

        //two textviews for the editable service and service rate
        final TextView serviceView = (TextView) findViewById(R.id.serviceNameEditField);
        serviceView.setText(EditServices.currentService);

        final TextView rateView = (TextView) findViewById(R.id.serviceRateEditField);
        rateView.setText(EditServices.currentServiceRate);

        confirm = (Button)findViewById(R.id.confirmChangesBtn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will need to make some validations on the new service name and rate before we actually add it
                //i.e. if the new service we are trying to add is blank or if it already exists, we make a toast saying it was not added (and ofc it wasnt added)

                MainActivity.ser.getServices().set(indexToChange, serviceView.getText().toString());
                MainActivity.ser.getServiceRates().set(indexToChange, rateView.getText().toString());


                //TODO make a toast confirming that it has been edited
                //TODO add a verification/validation to check that what we are editing is valid
                //TODO if its not valid, make a toast saying invalid
            }
        });

    }
}
