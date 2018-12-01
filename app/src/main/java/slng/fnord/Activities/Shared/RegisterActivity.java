package slng.fnord.Activities.Shared;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Optional;

import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Managers.AccountManager;
import slng.fnord.R;
import slng.fnord.Structures.User;
import slng.fnord.Helpers.Enums.UserTypes;

import static slng.fnord.Helpers.Enums.UserTypes.*;

public class RegisterActivity extends AppCompatActivity {
    String email;
    String username;
    String password;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AccountManager manager = new AccountManager(new DBHelper());

        //creating a spinner/dropdown list for the register screen
        final Spinner accountSpinner = findViewById(R.id.accountTypeSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.accountTypes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(myAdapter);

        //making register button on register screen work/add stuff to account Accounts arraylists
        //it shall open the appropriate welcome screen, as well as add the account to the list
        Button register2 = findViewById(R.id.registerButton);
        register2.setOnClickListener(view -> {
            EditText emailText = findViewById(R.id.registerEmail);
            email = emailText.getText().toString();

            EditText passwordText = findViewById(R.id.registerPassword);
            password = passwordText.getText().toString();


            accountType = accountSpinner.getSelectedItem().toString();

            if (!Common.validateEmail(email)) {
                emailText.setError("Email is invalid");
                return;
            }

            if (!Common.validatePassword(password)) {
                passwordText.setError("Password is invalid");
                return;
            }

            UserTypes type;

            if (accountType.equals("HomeOwner")) {
                type = HOMEOWNER;
            } else  {
                type = SERVICEPROVIDER;
            }

            manager.newUser(email, password, type, this::handleCreate);
        });
    }

    public void handleCreate(Optional<User> user) {
        if (!user.isPresent()) {
            ((EditText) findViewById(R.id.registerEmail)).setError("An account with this email already exists");
            return;
        }

        Toast.makeText(getApplicationContext(), "New account has been made!", Toast.LENGTH_SHORT).show();

        SignInActivity.currentUser = user.get();
        openUserActivity(user.get().getType());
    }

    //opens welcome screen for the user
    public void openUserActivity(UserTypes type) {
        Intent intent = null;
        switch (type) {
            case HOMEOWNER:
                intent = new Intent(this, slng.fnord.Activities.HomeOwner.Welcome.class);
                break;
            case SERVICEPROVIDER:
                intent = new Intent(this, slng.fnord.Activities.ServiceProvider.Welcome.class);
                break;
        }
        startActivity(intent);
    }


}
