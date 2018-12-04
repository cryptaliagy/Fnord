package slng.fnord.Activities.ServiceProvider;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Optional;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.AccountManager;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Helpers.ServicesAndRatesAdapter;

public class ViewServices extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_service);

        AccountManager manager = new AccountManager(new DBHelper());
        ServicesManager servicesManager = new ServicesManager(new DBHelper());

        ArrayList<String> services = (ArrayList<String>) ((ServiceProvider) SignInActivity.currentUser).getServiceList();

        ListView lv = findViewById(R.id.listOfServices);
        ServicesAndRatesAdapter adapter = new ServicesAndRatesAdapter(this, R.layout.adapter_view_layout,
                services);
        lv.setAdapter(adapter);

        ServiceProvider user = (ServiceProvider) SignInActivity.currentUser;

        lv.setOnItemClickListener((parent, view, position, id) -> {
            String serviceName = services.get(position);
            boolean certified = user.isCertified(serviceName);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle(serviceName)
                    .setMultiChoiceItems(new CharSequence[]{"Certified"}, new boolean[]{certified},
                            (dialog, which, isChecked) -> user.updateCertified(serviceName, isChecked))
                    .setPositiveButton("Done", (dialog, buttonID) -> {
                        manager.updateUser(user);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Remove", (dialog, buttonID) -> {
                        user.removeService(serviceName);
                        manager.updateUser(user);
                        servicesManager.getService(serviceName, this::handleService);
                        adapter.remove(serviceName);
                        dialog.dismiss();
                    });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        });
    }

    public void handleService(Service service) {
        if (service != null) {
            ServiceProvider user = (ServiceProvider) SignInActivity.currentUser;
            service.deleteProvider(user.getCompany());
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.backSPViewService) {
            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
        } else if (v.getId() == R.id.addSPViewService) {
            Intent intent = new Intent(this, AddService.class);
            startActivity(intent);
        }
    }
}
