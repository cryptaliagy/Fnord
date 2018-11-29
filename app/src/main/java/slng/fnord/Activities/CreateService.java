package slng.fnord.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import io.reactivex.Observable;
import io.reactivex.Observer;
import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBHelper;
import slng.fnord.Database.DBObserver;
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
        final Services ser = MainActivity.getServices();

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
                Toast toast;

                if (!Common.validateService(serviceToAdd)) {
                    toast = Toast.makeText(getApplicationContext(), "Service name invalid", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (!Common.validatePrice(rateOfService)) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Service price invalid, must be a number with at most 2 decimal places", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                final String id = Common.makeMD5(serviceToAdd);
                Observable<DataSnapshot> observable = DBHelper.makeObservableFromPath("services/"+id);
                Observer<DataSnapshot> observer = new DBObserver<DataSnapshot>() {
                    @Override
                    public void onNext(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "No Service created. Service already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Service service = new Service(serviceToAdd, Double.valueOf(rateOfService));
                            dataSnapshot.getRef().setValue(service);
                            ser.addService(service);
                            Toast.makeText(getApplicationContext(), "Service Created.", Toast.LENGTH_SHORT).show();
                        }

                    }
                };

                observable.subscribe(observer);
            }
        });

    }
}
