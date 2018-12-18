package slng.fnord.Activities.Shared;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import slng.fnord.R;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting register button on main screen to open to the register screen
        register = findViewById(R.id.registerChoiceButton);
        register.setOnClickListener(view -> openActivity(RegisterActivity.class));

        //setting sign in button on main screen to open to the sign in screen
        signIn = findViewById(R.id.signInChoiceButton);
        signIn.setOnClickListener(view -> openActivity(SignInActivity.class));

    }

    public void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}
