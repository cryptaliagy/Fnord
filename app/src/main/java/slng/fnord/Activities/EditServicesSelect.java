package slng.fnord.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import slng.fnord.Helpers.Common;
import slng.fnord.R;
import slng.fnord.Structures.Services;

public class EditServicesSelect extends AppCompatActivity {
    private Spinner editServicesSpinner;
    private Button editService;
    //two static strings for the service that is selected and its rate - need these so that we can edit them in the next
    //screen and then set them back into the service/servicerates arraylists
    public static String currentService;
    public static String currentServiceRate;
    public static Services ser = MainActivity.getServices();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services);
        initializeSpinner();

        editService = (Button) findViewById(R.id.editServiceBtn);
        editService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentService = editServicesSpinner.getSelectedItem().toString();
                currentServiceRate = Double.toString(ser.getServiceRate(Common.makeMD5(currentService)));
                openEditServicesView();
            }
        });

    }

    private void initializeSpinner(){
        editServicesSpinner = (Spinner) findViewById(R.id.editServiceSpinner);
        ArrayList<String> services = ser.asArrayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editServicesSpinner.setAdapter(adapter);

    }

    public void openEditServicesView(){
        Intent intent = new Intent(this, EditService.class);
        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        initializeSpinner();
    }



}
