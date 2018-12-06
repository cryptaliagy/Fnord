package slng.fnord.Activities.ServiceProvider;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Optional;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Activities.Shared.Welcome;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Managers.AccountManager;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Helpers.ServicesAndRatesAdapter;

public class ViewServices extends AppCompatActivity {
    private ArrayList<String> allServices;
    private AccountManager accountManager;
    private ServicesManager servicesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_service);

        accountManager = new AccountManager(new DBHelper());
        servicesManager = new ServicesManager(new DBHelper());

        servicesManager.getServiceNamesArrayList(this::setAllServices);
    }

    public void handleService(Service service) {
        if (service != null) {
            ServiceProvider user = (ServiceProvider) Welcome.currentUser;
            service.deleteProvider(user.getCompany());
        }
    }

    public void backClick(View v) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }

    public void addService(View v) {
        Spinner spinner = new Spinner(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Common.dpToPx(this, 300),
                LinearLayout.LayoutParams.MATCH_PARENT);
        spinner.setLayoutParams(lp);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allServices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        LinearLayout layoutView = new LinearLayout(ViewServices.this);
        layoutView.setOrientation(LinearLayout.VERTICAL);
        layoutView.addView(spinner);
        layoutView.setPadding(Common.dpToPx(this, 20), 0, 0, 0);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Add a service")
                .setView(layoutView)
                .setPositiveButton("Add", (dialog, which) -> {
                    if (spinner.getSelectedItem() == null) {
                        Toast.makeText(getApplicationContext(), "No selected service", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String currentService = spinner.getSelectedItem().toString();
                    ServiceProvider provider = (ServiceProvider) Welcome.currentUser;

                    if (provider.getServiceList().contains(currentService)) {
                        Toast.makeText(getApplicationContext(), "Service has already been added", Toast.LENGTH_SHORT).show();
                    } else {
                        currentService = spinner.getSelectedItem().toString();

                        provider.addService(currentService, false);
                        accountManager.updateUser(provider);
                        Toast.makeText(getApplicationContext(), "Service has been added", Toast.LENGTH_SHORT).show();
                        initializeUI();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();


    }

    private void setAllServices(ArrayList<String> allServices) {
        if (allServices != null) {
            this.allServices = allServices;
        } else {
            this.allServices = new ArrayList<>();
        }

        initializeUI();
    }

    private void initializeUI() {
        ArrayList<String> services = (ArrayList<String>) ((ServiceProvider) Welcome.currentUser).getServiceList();

        ListView lv = findViewById(R.id.listOfServices);
        ServicesAndRatesAdapter adapter = new ServicesAndRatesAdapter(this, R.layout.adapter_view_layout,
                services);
        lv.setAdapter(adapter);

        ServiceProvider user = (ServiceProvider) Welcome.currentUser;

        lv.setOnItemClickListener((parent, view, position, id) -> {
            String serviceName = services.get(position);
            boolean certified = user.isCertified(serviceName);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle(serviceName)
                    .setMultiChoiceItems(new CharSequence[]{"Certified"}, new boolean[]{certified},
                            (dialog, which, isChecked) -> user.updateCertified(serviceName, isChecked))
                    .setPositiveButton("Done", (dialog, buttonID) -> {
                        accountManager.updateUser(user);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Remove", (dialog, buttonID) -> {
                        user.removeService(serviceName);
                        accountManager.updateUser(user);
                        servicesManager.getService(serviceName, this::handleService);
                        adapter.remove(serviceName);
                        dialog.dismiss();
                    });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        });
    }
}
