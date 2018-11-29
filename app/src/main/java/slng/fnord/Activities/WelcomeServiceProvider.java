package slng.fnord.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import slng.fnord.R;

public class WelcomeServiceProvider extends AppCompatActivity {
    private Button SPViewProfile;
    private Button SPEditProfile;
    private Button SPAddService;
    private Button SPDeleteService;
    private Button SPViewService;
    private Button SPAddAvailability;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_service_provider);
        //setting a custom message showing the username of the account
        TextView textView = (TextView) findViewById(R.id.welcomeSP);
        textView.setText("Welcome " + SignInActivity.currentUser.getUsername() + ".");

        SPViewProfile = (Button) findViewById(R.id.SPViewInfoChoice);
        SPViewProfile.setOnClickListener(view -> openView(SPViewProfile.class));

        SPEditProfile = (Button) findViewById(R.id.SPEditInfoChoice);
        SPEditProfile.setOnClickListener(view -> openView(SPEditProfile.class));

        SPViewService = (Button) findViewById(R.id.SPViewServiceChoice);
        SPViewService.setOnClickListener(view -> openView(SPViewService.class));

        SPAddAvailability = (Button) findViewById(R.id.SPAddAvailabilitiesChoice);
        SPAddAvailability.setOnClickListener(view -> openView(SPAddAvailability.class));




    }


    public void openView(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}
