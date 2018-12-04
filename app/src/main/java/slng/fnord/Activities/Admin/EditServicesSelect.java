package slng.fnord.Activities.Admin;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Optional;

import slng.fnord.Activities.Shared.MainActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.Services;

public class EditServicesSelect extends AppCompatActivity {
    private Spinner editServicesSpinner;
    private Button editService;
    //two static strings for the service that is selected and its rate - need these so that we can edit them in the next
    //screen and then set them back into the service/servicerates arraylists
    public static String currentService;
    private ServicesManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services);
        manager = new ServicesManager(new DBHelper());

        editService = findViewById(R.id.editServiceBtn);
        editService.setOnClickListener(view -> {
            currentService = editServicesSpinner.getSelectedItem().toString();
            openEditServicesView();
        });

        manager.getServiceNamesArrayList(this::initializeSpinner);

    }

    private void initializeSpinner(Optional<ArrayList<String>> servicesOptional) {
        ArrayList<String> services;
        if (!servicesOptional.isPresent()) {
            services = new ArrayList<>();
        } else {
            services = servicesOptional.get();
        }
        editServicesSpinner = findViewById(R.id.editServiceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editServicesSpinner.setAdapter(adapter);

    }

    public void openEditServicesView() {
        Intent intent = new Intent(this, EditService.class);
        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        manager.getServiceNamesArrayList(this::initializeSpinner);
    }


}
