package slng.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        SPViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openView(SPViewProfile.class);
            }
        });

        SPEditProfile = (Button) findViewById(R.id.SPEditInfoChoice);
        SPEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openView(SPEditProfile.class);
            }
        });

        SPAddService = (Button) findViewById(R.id.SPAddServiceChoice);
        SPAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openView(SPAddService.class);
            }
        });

        SPDeleteService = (Button) findViewById(R.id.SPDeleteServiceChoice);
        SPDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openView(SPDeleteService.class);
            }
        });

        SPViewService = (Button) findViewById(R.id.SPViewServiceChoice);
        SPViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openView(SPViewService.class);
            }
        });

        SPAddAvailability = (Button) findViewById(R.id.SPAddAvailabilitiesChoice);
        SPAddAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openView(SPAddAvailability.class);
            }
        });




    }


    public void openView(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}
