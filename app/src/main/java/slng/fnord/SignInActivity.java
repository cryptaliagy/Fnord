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

                //checking if the email is in home owners email arraylist
                if(MainActivity.acc.getHomeOwnerEmails().contains(email)){
                    //getting index of the email
                    int emailIndex = MainActivity.acc.getHomeOwnerEmails().indexOf(email);
                    //checking if the password passed into sign in form is the same as the password
                    //with index corresponding to the email
                    if(MainActivity.acc.getHomeOwnerPasswords().get(emailIndex).equals(password)){
                        currentUser = MainActivity.acc.getHomeOwnerUsernames().get(emailIndex);
                        openHomeOwnerActivity();
                    }
                }

                //checking if the email is in service providers email arraylist
                else if(MainActivity.acc.getServiceProviderEmails().contains(email)){
                    //getting index of the email
                    int emailIndex = MainActivity.acc.getServiceProviderEmails().indexOf(email);
                    //checkings password
                    if(MainActivity.acc.getServiceProviderPasswords().get(emailIndex).equals(password)){
                        currentUser = MainActivity.acc.getServiceProviderUsernames().get(emailIndex);
                        openServiceProviderActivity();
                    }
                }
                else if(MainActivity.acc.getAdminEmail().equals(email)){
                    if(MainActivity.acc.getAdminPassword().equals(password)){
                        currentUser = MainActivity.acc.getAdminUsername();
                        openAdministratorActivity();
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
