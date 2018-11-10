package slng.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button signIn;
    public static Accounts acc = new Accounts(); //static instance of account created to keep arraylists of all accounts
    public static Services ser = new Services(); //static instance of services created to keep arraylists of all services/rates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting register button on main screen to open to the register screen
        register = (Button) findViewById(R.id.registerChoiceButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        //setting sign in button on main screen to open to the sign in screen
        signIn = (Button) findViewById(R.id.signInChoiceButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInActivity();
            }
        });
    }

    public static Accounts getAccounts(){
        return acc;
    }

    //opening the register screen
    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //opening the sign in screen
    public void openSignInActivity(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
