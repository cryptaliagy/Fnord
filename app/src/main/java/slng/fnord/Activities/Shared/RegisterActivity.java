package slng.fnord.Activities.Shared;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
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
    String companyName;
    String password;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AccountManager manager = new AccountManager(new DBHelper());

        String[] accountTypes = getResources().getStringArray(R.array.accountTypes);

        //creating a spinner/dropdown list for the register screen
        final Spinner accountSpinner = findViewById(R.id.accountTypeSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, accountTypes);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(myAdapter);

        EditText companyText = findViewById(R.id.companyNameRegisterEditText);

        Button register2 = findViewById(R.id.registerButton);
        register2.setOnClickListener(view -> {
            EditText emailText = findViewById(R.id.registerEmail);
            email = emailText.getText().toString();

            EditText passwordText = findViewById(R.id.registerPassword);
            password = passwordText.getText().toString();

            companyName = companyText.getText().toString();

            accountType = accountSpinner.getSelectedItem().toString();

            if (!Common.validateEmail(email)) {
                emailText.setError("Email is invalid");
                return;
            }

            if (!Common.validatePassword(password)) {
                passwordText.setError("Password is invalid");
                return;
            }

            if (accountType.equals("Service Provider") && !Common.validateCompany(companyName)) {
                companyText.setError("Company name contains forbidden characters");
                return;
            }

            if (accountType.equals("Home Owner")) {
                manager.newHomeOwner(email, password, this::handleCreate);
            } else  {
                manager.newServiceProvider(email, password, companyName, this::handleCreate);
            }
        });

        accountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (accountSpinner.getSelectedItem().toString().equals("Service Provider")) {
                    companyText.setVisibility(View.VISIBLE);
                } else {
                    companyText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void handleCreate(Optional<User> user) {
        if (!user.isPresent()) {
            ((EditText) findViewById(R.id.registerEmail)).setError("An account with this email already exists");
            return;
        }
        Toast.makeText(getApplicationContext(), "New account has been made!", Toast.LENGTH_SHORT).show();

        Welcome.currentUser = user.get();
        openUserActivity(user.get().getType());
    }

    //opens welcome screen for the user
    public void openUserActivity(UserTypes type) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }


}
