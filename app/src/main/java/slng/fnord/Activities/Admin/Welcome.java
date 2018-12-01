package slng.fnord.Activities.Admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.R;

public class Welcome extends AppCompatActivity {
    private Button cserve;
    private Button dserve;
    private Button vserve;
    private Button eserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_administrator);
        //setting a custom message showing the username of the account
        TextView textView = (TextView) findViewById(R.id.welcomeAdmin);
        textView.setText("Welcome " + SignInActivity.currentUser.getEmail() + ".");

        cserve = findViewById(R.id.createServiceChoiceButton);
        cserve.setOnClickListener(view -> openView(CreateService.class));

        vserve = findViewById(R.id.viewServicesButton);
        vserve.setOnClickListener(view -> openView(ViewService.class));

        eserve = findViewById(R.id.editServiceChoiceButton);
        eserve.setOnClickListener(view -> openView(EditServicesSelect.class));
    }

    public void openView(final Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}
