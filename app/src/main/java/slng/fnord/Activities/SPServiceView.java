package slng.fnord.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import slng.fnord.Database.DBHelper;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class SPServiceView extends Activity {

    public static String serviceName;
    public static Double serviceRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spservice_view);

        TextView serviceText = findViewById(R.id.serviceNameTextView);
        TextView rateText = findViewById(R.id.rateValueTextView);

        serviceText.setText(serviceName);
        rateText.setText(String.format("%.2f", serviceRate));
    }

    public void onClick(View v) {
        if (v.getId() == R.id.backSPServiceView) {
            // Cleanup, just in case
            serviceRate = null;
            serviceName = null;
            Intent intent = new Intent(this, SPViewService.class);
            startActivity(intent);
        } else if (v.getId() == R.id.removeSPServiceView) {
            if (((ServiceProvider) SignInActivity.currentUser).getServiceList().contains(serviceName)) {
                ((ServiceProvider) SignInActivity.currentUser).removeService(serviceName);
                DBHelper.updateUser(SignInActivity.currentUser);
                Toast.makeText(getApplicationContext(), "Service removed", Toast.LENGTH_SHORT).show();
                DBHelper.updateUser(SignInActivity.currentUser);
                Intent intent = new Intent(this, SPViewService.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Error Removing Service", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
