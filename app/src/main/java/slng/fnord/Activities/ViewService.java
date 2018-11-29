package slng.fnord.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import slng.fnord.R;
import slng.fnord.Helpers.ServicesAndRatesAdapter;

//simple view of service + its rate, do not touch
public class ViewService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);

        ListView lv = (ListView) findViewById(R.id.listOfServices);
        ServicesAndRatesAdapter adapter = new ServicesAndRatesAdapter(this, R.layout.adapter_view_layout,
                MainActivity.getServices().asArrayList());
        lv.setAdapter(adapter);
    }
}
