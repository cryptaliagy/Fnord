package slng.fnord.Activities.ServiceProvider;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import slng.fnord.Activities.Shared.MainActivity;
import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.R;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button SPViewProfile;
        Button SPViewService;
        Button SPAddAvailability;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_service_provider);
        //setting a custom message showing the username of the account
        TextView textView = findViewById(R.id.welcomeSP);
        textView.setText("Welcome " + SignInActivity.currentUser.getEmail() + ".");

        SPViewProfile = findViewById(R.id.SPViewInfoChoice);
        SPViewProfile.setOnClickListener(view -> openActivity(ViewProfile.class));

        SPViewService = findViewById(R.id.SPViewServiceChoice);
        SPViewService.setOnClickListener(view -> openActivity(ViewServices.class));

        SPAddAvailability = findViewById(R.id.SPAddAvailabilitiesChoice);
        SPAddAvailability.setOnClickListener(view -> openActivity(Availability.class));


        findViewById(R.id.logOutButtonServiceProvier).setOnClickListener(view -> {
            SignInActivity.currentUser = null;
            openActivity(MainActivity.class);
        });

    }


    public void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}
