package slng.fnord.Activities.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import slng.fnord.Activities.Shared.MainActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Helpers.ServicesAndRatesAdapter;

//simple view of service + its rate, do not touch
public class ViewService extends AppCompatActivity {
    ServicesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);

        manager = new ServicesManager(new DBHelper());

        manager.getServiceNamesArrayList(this::updateUI);

    }

    public void updateUI(ArrayList<String> services) {
        ListView lv = findViewById(R.id.listOfServices);
        ServicesAndRatesAdapter adapter = new ServicesAndRatesAdapter(this, R.layout.adapter_view_layout,
                services);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            String serviceName = services.get(position);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle(serviceName)
                    .setPositiveButton("Done", (dialog, buttonID) -> {
                        dialog.dismiss();
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
}
