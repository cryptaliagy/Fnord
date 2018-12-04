package slng.fnord.Activities.ServiceProvider;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.R;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button SPViewProfile;
        Button SPEditProfile;
        Button SPAddService;
        Button SPDeleteService;
        Button SPViewService;
        Button SPAddAvailability;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_service_provider);
        //setting a custom message showing the username of the account
        TextView textView = findViewById(R.id.welcomeSP);
        textView.setText("Welcome " + SignInActivity.currentUser.getEmail() + ".");

        SPViewProfile = findViewById(R.id.SPViewInfoChoice);
        SPViewProfile.setOnClickListener(view -> openView(ViewProfile.class));

        SPEditProfile = findViewById(R.id.SPEditInfoChoice);
        SPEditProfile.setOnClickListener(view -> openView(EditProfile.class));

        SPViewService = findViewById(R.id.SPViewServiceChoice);
        SPViewService.setOnClickListener(view -> openView(ViewServices.class));

        SPAddAvailability = findViewById(R.id.SPAddAvailabilitiesChoice);
        SPAddAvailability.setOnClickListener(view -> openView(Availability.class));




    }


    public void openView(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}
