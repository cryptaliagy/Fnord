package slng.fnord.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import slng.fnord.Helpers.Common;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Helpers.ServicesAndRatesAdapter;

public class SPViewService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_service);

        ArrayList<String> services = (ArrayList<String>) ((ServiceProvider) SignInActivity.currentUser).getServiceList();

        ListView lv = (ListView) findViewById(R.id.listOfServices);
        ServicesAndRatesAdapter adapter = new ServicesAndRatesAdapter(this, R.layout.adapter_view_layout,
                services);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            String serviceName = services.get(position);
            SPServiceView.serviceName = serviceName;
            SPServiceView.serviceRate = MainActivity.getServices().getServiceRate(Common.makeMD5(serviceName));
            Intent intent = new Intent(this, SPServiceView.class);
            startActivity(intent);
        });
    }

    protected void onClick(View v) {
        if (v.getId() == R.id.backSPViewService) {
            Intent intent = new Intent(this, WelcomeServiceProvider.class);
            startActivity(intent);
        } else if (v.getId() == R.id.addSPViewService) {
            Intent intent = new Intent(this, SPAddService.class);
            startActivity(intent);
        }
    }
}
