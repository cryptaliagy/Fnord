package slng.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    private Button signIn2;
    String email;
    String password;
    public static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn2 = (Button) findViewById(R.id.signInButton);
        signIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = (EditText) findViewById(R.id.signInEmail);
                email = emailText.getText().toString();
                EditText passwordText = (EditText) findViewById(R.id.signInPassword);
                password = passwordText.getText().toString();
                Accounts acc = MainActivity.getAccounts();
                User user = null;
                if (acc.existsAccount(email)) {
                    user = acc.getUser(email);
                    currentUser = user.getUsername();
                    switch(user.getType()) {
                        case ADMIN:
                            openAdministratorActivity();
                            break;
                        case HOMEOWNER:
                            openHomeOwnerActivity();
                            break;
                        case SERVICEPROVIDER:
                            openServiceProviderActivity();
                            break;
                    }
                }
            }
        });



    }


    public void openHomeOwnerActivity(){
        Intent intent = new Intent(this, WelcomeHomeOwner.class);
        startActivity(intent);
    }

    public void openServiceProviderActivity(){
        Intent intent = new Intent(this, WelcomeServiceProvider.class);
        startActivity(intent);
    }

    public void openAdministratorActivity(){
        Intent intent = new Intent(this, WelcomeAdministrator.class);
        startActivity(intent);
    }


}
