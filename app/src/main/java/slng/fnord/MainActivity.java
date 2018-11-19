package slng.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button signIn;
    private static Accounts acc = new Accounts();
    private static Services ser = new Services();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting register button on main screen to open to the register screen
        register = (Button) findViewById(R.id.registerChoiceButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Availability.class); // TEMP
                //openActivity(RegisterActivity.class);
            }
        });

        //setting sign in button on main screen to open to the sign in screen
        signIn = (Button) findViewById(R.id.signInChoiceButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SignInActivity.class);
            }
        });
    }

    public static Accounts getAccounts() { return acc; }

    public static Services getServices() { return ser; }

    public void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}
