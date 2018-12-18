package slng.fnord.Activities.Shared;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import slng.fnord.Activities.ServiceProvider.ViewProfile;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Managers.AccountManager;
import slng.fnord.R;
import slng.fnord.Structures.User.User;
import slng.fnord.Helpers.Enums.UserTypes;

public class RegisterActivity extends AppCompatActivity {
    String email;
    String companyName;
    String password;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AccountManager manager = AccountManager.getInstance();

        String[] accountTypes = getResources().getStringArray(R.array.accountTypes);

        //creating a spinner/dropdown list for the register screen
        final Spinner accountSpinner = findViewById(R.id.accountTypeSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, accountTypes);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(myAdapter);

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

            if (accountType.equals("Home Owner")) {
                manager.newUser(email, password, UserTypes.HOMEOWNER, this::handleCreate);
            } else  {
                manager.newUser(email, password, UserTypes.SERVICEPROVIDER, this::handleCreate);
            }
        });
    }

    public void handleCreate(User user) {
        if (user == null) {
            ((EditText) findViewById(R.id.registerEmail)).setError("An account with this email already exists");
            return;
        }
        Toast.makeText(getApplicationContext(), "New account has been made!", Toast.LENGTH_SHORT).show();

        Welcome.currentUser = user;
        openUserActivity(user.getType());
    }

    //opens welcome screen for the user
    public void openUserActivity(UserTypes type) {
        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);
    }


}
