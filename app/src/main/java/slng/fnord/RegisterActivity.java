package slng.fnord;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private Button register2;
    String email;
    String username;
    String password;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //creating a spinner/dropdown list for the register screen
        final Spinner accountSpinner = (Spinner) findViewById(R.id.accountTypeSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.accountTypes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(myAdapter);

        //making register button on register screen work/add stuff to account Accounts arraylists
        //it shall open the appropriate welcome screen, as well as add the account to the list
        register2 = (Button) findViewById(R.id.registerButton);
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = (EditText) findViewById(R.id.registerEmail);
                email = emailText.getText().toString();
                EditText passwordText = (EditText) findViewById(R.id.registerPassword);
                password = passwordText.getText().toString();
                EditText usernameText = (EditText) findViewById(R.id.registerUsername);
                username = usernameText.getText().toString();
                accountType = accountSpinner.getSelectedItem().toString();

                //TODO here we will probably need to add validations - i.e. for the variables above (email, pwd, username), we will
                //TODO need to check whether they are actually valid before proceeding onward
                //TODO also we may need to implement another function to make sure the username/email we are registering as does not already
                //TODO exist in the appropriate arraylist for the account being created (i.e. service provider/homeowner)

                //three different cases depending on what was selected
                //in each case, we add the email/username/password to the appropriate arraylist in the static instance of Accounts
                if(accountType.equals("HomeOwner")){
                    MainActivity.acc.addHomeOwnerEmail(email);
                    MainActivity.acc.addHomeOwnerUsernames(username);
                    MainActivity.acc.addHomeOwnerPassword(password);
                    SignInActivity.currentUser = username;
                    openHomeOwnerActivity();

                }
                else if(accountType.equals("ServiceProvider")){
                    MainActivity.acc.addSPEmail(email);
                    MainActivity.acc.addSPUsernames(username);
                    MainActivity.acc.addSPPassword(password);
                    SignInActivity.currentUser = username;
                    openServiceProviderActivity();
                }
                else if(accountType.equals("Administrator") && MainActivity.acc.adminFlag==false){
                    MainActivity.acc.addAdminEmail(email);
                    MainActivity.acc.addAdminUsername(username);
                    MainActivity.acc.addAdminPassword(password);
                    SignInActivity.currentUser = username;
                    MainActivity.acc.adminFlag = true;
                    openAdministratorActivity();
                }
//                else{
//                    System.exit(0);
//                }

            }
        });
    }
    //opens welcome screen for a homeowner
    public void openHomeOwnerActivity(){
        Intent intent = new Intent(this, WelcomeHomeOwner.class);
        startActivity(intent);
    }

    //opens welcome screen for a service provider
    public void openServiceProviderActivity(){
        Intent intent = new Intent(this, WelcomeServiceProvider.class);
        startActivity(intent);
    }

    //opens welcome screen for an admin
    public void openAdministratorActivity(){
        Intent intent = new Intent(this, WelcomeAdministrator.class);
        startActivity(intent);
    }



}
