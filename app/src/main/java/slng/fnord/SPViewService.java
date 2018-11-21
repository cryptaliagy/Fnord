package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SPViewService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_service);

        ListView lv = (ListView) findViewById(R.id.listOfServices);
        ServicesAndRatesAdapter adapter = new ServicesAndRatesAdapter(this, R.layout.adapter_view_layout,
                (ArrayList)((ServiceProvider) SignInActivity.currentUser).getServiceList());
        lv.setAdapter(adapter);
    }
}
