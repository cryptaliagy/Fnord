package slng.fnord.Activities.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Optional;

import slng.fnord.Activities.Shared.MainActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Helpers.ServicesAndRatesAdapter;
import slng.fnord.Structures.Service;

//simple view of service + its rate, do not touch
public class ViewService extends AppCompatActivity {
    private ServicesManager manager;
    private String newServiceName;
    private String previousServiceName;
    private String rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);
        manager = new ServicesManager(new DBHelper());

        manager.getServiceNamesArrayList(this::updateUI);

    }

    public void updateUI(ArrayList<String> servicesOptional) {
        ArrayList<String> services;
        if (servicesOptional == null) {
            services = new ArrayList<>();
        } else {
            services = servicesOptional;
        }

        ListView lv = findViewById(R.id.listOfServices);
        ServicesAndRatesAdapter adapter = new ServicesAndRatesAdapter(this, R.layout.adapter_view_layout,
                services);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener((parent, view, position, id) -> {
            String serviceName = services.get(position);

            EditText serviceText = new EditText(ViewService.this);
            EditText rateText = new EditText(ViewService.this);


            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Common.dpToPx(this, 300),
                    LinearLayout.LayoutParams.MATCH_PARENT);

            serviceText.setLayoutParams(lp);
            serviceText.setHint("Service name");

            rateText.setLayoutParams(lp);
            rateText.setHint("Service rate");

            serviceText.setText(serviceName);
            manager.getServiceRateForView(serviceName, rateText);

            LinearLayout layoutView = new LinearLayout(ViewService.this);

            layoutView.setOrientation(LinearLayout.VERTICAL);
            layoutView.addView(serviceText);
            layoutView.addView(rateText);
            layoutView.setPadding(Common.dpToPx(this, 20), 0, 0, 0);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Edit Service")
                    .setView(layoutView)
                    .setPositiveButton("Done", (dialog, buttonID) -> {
                        if (serviceText.getText().toString().isEmpty() || rateText.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "That Field Cannot Be Blank.",
                                    Toast.LENGTH_SHORT).show();
                            return;

                        }
                        newServiceName = serviceText.getText().toString();
                        previousServiceName = serviceName;
                        rate = rateText.getText().toString();

                        if (!Common.validateService(newServiceName)) {
                            Toast.makeText(getApplicationContext(), "The service name is invalid",
                                    Toast.LENGTH_SHORT).show();
                        } else if (!Common.validatePrice(rate)) {
                            Toast.makeText(getApplicationContext(), "The service rate is invalid",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (!previousServiceName.equals(newServiceName)) {
                                manager.getService(newServiceName, this::handleServiceConflict);
                            } else {
                                manager.getService(previousServiceName, this::updateServiceObject);
                            }
                        }
                    })
                    .setNegativeButton("Remove", (dialog, buttonID) -> {
                        manager.removeService(serviceName);
                        adapter.remove(serviceName);
                        dialog.dismiss();
                    });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        });

    }

    public void addNew(View view) {
        EditText serviceText = new EditText(ViewService.this);
        EditText rateText = new EditText(ViewService.this);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Common.dpToPx(this, 300),
                LinearLayout.LayoutParams.MATCH_PARENT);

        serviceText.setLayoutParams(lp);
        serviceText.setHint("Service name");

        rateText.setLayoutParams(lp);
        rateText.setHint("Service rate");
        serviceText.setText("");
        rateText.setText("");

        LinearLayout layoutView = new LinearLayout(ViewService.this);

        layoutView.setOrientation(LinearLayout.VERTICAL);
        layoutView.addView(serviceText);
        layoutView.addView(rateText);
        layoutView.setPadding(Common.dpToPx(this, 20), 0, 0, 0);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Add new service")
                .setView(layoutView)
                .setPositiveButton("Done", (dialog, buttonID) -> {
                    String serviceToAdd = serviceText.getText().toString();
                    String rateOfService = rateText.getText().toString();

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
                })
                .setNegativeButton("Cancel", (dialog, buttonID) -> {
                    dialog.dismiss();
                });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void creationCallback(Service service) {
        if (service == null) {
            Toast.makeText(getApplicationContext(), "A service with that name already exists",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Service created!", Toast.LENGTH_SHORT).show();
            manager.getServiceNamesArrayList(this::updateUI);
        }

    }

    private void handleServiceConflict(Service service) {
        if (service == null) {
            manager.getService(previousServiceName, this::updateServiceObject);
        } else {
            Toast.makeText(getApplicationContext(), "A service with this name already exists", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateServiceObject(Service service) {
        if (service == null) {
            return;
        }
        service.setServiceName(newServiceName);
        service.setServiceRate(Double.valueOf(rate));
        manager.updateService(service);
        manager.getServiceNamesArrayList(this::updateUI);

    }
}
