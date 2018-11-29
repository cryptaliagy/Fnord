package slng.fnord.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import slng.fnord.R;

public class WelcomeHomeOwner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_home_owner);
        //setting a custom message showing the username of the account
        TextView textView = findViewById(R.id.welcomeHO);
        textView.setText("Welcome " + SignInActivity.currentUser.getUsername() + ".");
    }
}
