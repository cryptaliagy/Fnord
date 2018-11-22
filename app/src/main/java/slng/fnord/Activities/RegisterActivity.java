package slng.fnord.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBHelper;
import slng.fnord.Database.DBObserver;
import slng.fnord.R;
import slng.fnord.Structures.User;
import slng.fnord.Structures.UserTypes;

import static slng.fnord.Structures.UserTypes.*;

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

                Toast toast = null;

                if (!Common.validateEmail(email)) {
                    showToast("Email is invalid");
                    return;
                }

                if (!Common.validatePassword(password)) {
                    showToast("Password is invalid");
                    return;
                }

                if (!Common.validateUser(username)) {
                    showToast("Username is invalid");
                    return;
                }

                UserTypes type = null;

                if (accountType.equals("HomeOwner")) {
                    type = HOMEOWNER;
                } else if (accountType.equals("ServiceProvider")) {
                    type = SERVICEPROVIDER;
                }

                User user = Common.makeUser(email, username, password, type);

                checkEmailExists(user);
            }
        });
    }

    public void checkEmailExists(final User user) {
        String id = Common.makeMD5(user.getEmail());
        Observable<DataSnapshot> userObservable = DBHelper.makeObservableFromPath("users/"+id);

        DBObserver<DataSnapshot> userObserver = new DBObserver<DataSnapshot>() {
            @Override
            public void onNext(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showToast("An account with this email already exists");
                } else {
                    checkUsernameExists(user);
                }
            }
        };

        userObservable.subscribe(userObserver);

    }

    public void checkUsernameExists(final User user) {
        Observable<DataSnapshot> lookupObservable = DBHelper.makeObservableFromPath("lookup/"+user.getUsername());

        DBObserver<DataSnapshot> lookupObserver = new DBObserver<DataSnapshot>() {
            @Override
            public void onNext(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showToast("An account with this username already exists");
                } else {
                    registerUser(user);
                }
            }
        };

        lookupObservable.subscribe(lookupObserver);

    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //opens welcome screen for the user
    public void openUserActivity(UserTypes type) {
        Intent intent = null;
        switch (type) {
            case HOMEOWNER:
                intent = new Intent(this, WelcomeHomeOwner.class);
                break;
            case SERVICEPROVIDER:
                intent = new Intent(this, WelcomeServiceProvider.class);
                break;
        }
        startActivity(intent);
    }

    public void registerUser(final User user) {
        String id = Common.makeMD5(user.getEmail());

        DBHelper.makeCompletableFromPath("users/" + id, user).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");

            }

            @Override
            public void onComplete() {
                SignInActivity.currentUser = user;
                showToast("New account as been made");
                openUserActivity(user.getType());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }
        });

        DBHelper.makeCompletableFromPath("lookup/"+user.getUsername(), id).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }


}
