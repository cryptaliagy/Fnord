package slng.fnord.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import slng.fnord.Helpers.Common;
import slng.fnord.Helpers.Pair;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class SPAddAvailability extends AppCompatActivity {
    ServiceProvider serviceProvider = (ServiceProvider) SignInActivity.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spadd_availability);


    }

    public void onClick(View view) {

    }
}
