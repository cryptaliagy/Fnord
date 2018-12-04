package slng.fnord.Activities.HomeOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_home_owner);
        //setting a custom message showing the username of the account
        TextView textView = findViewById(R.id.welcomeHO);
        textView.setText("Welcome " + SignInActivity.currentUser.getEmail() + ".");
    }

    public void viewBookings(View view){
        Intent intent = null;
        intent = new Intent(this, slng.fnord.Activities.HomeOwner.BookingList.class);
        startActivity(intent);
    }
}
