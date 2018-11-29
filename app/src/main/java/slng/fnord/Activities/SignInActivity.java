package slng.fnord.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import io.reactivex.Observable;
import slng.fnord.Structures.Administrator;
import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBHelper;
import slng.fnord.Database.DBObserver;
import slng.fnord.Structures.HomeOwner;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.User;
import slng.fnord.Structures.UserTypes;

public class SignInActivity extends AppCompatActivity {
    private Button signIn2;
    String email;
    String password;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn2 = findViewById(R.id.signInButton);
        signIn2.setOnClickListener(view -> {
            EditText emailText = findViewById(R.id.signInEmail);
            EditText passwordText = findViewById(R.id.signInPassword);

            password = passwordText.getText().toString();
            email = emailText.getText().toString();

            if (!Common.validateEmail(email)) {
                Toast toastInvalidEmail = Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT);
                toastInvalidEmail.show();
                return;
            }

            if (!Common.validatePassword(password)) {
                Toast toastInvalidPassword = Toast.makeText(getApplicationContext(), "Password is invalid", Toast.LENGTH_SHORT);
                toastInvalidPassword.show();
                return;
            }

            String id = Common.makeMD5(email);

            Observable<DataSnapshot> userObservable = DBHelper.makeObservableFromPath("users/" + id);
            DBObserver<DataSnapshot> userObserver = new DBObserver<DataSnapshot>() {
                @Override
                public void onNext(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User dbUser = null;
                        UserTypes type = dataSnapshot.child("type").getValue(UserTypes.class);
                        switch (type) {
                            case ADMIN:
                                dbUser = dataSnapshot.getValue(Administrator.class);
                                break;
                            case HOMEOWNER:
                                dbUser = dataSnapshot.getValue(HomeOwner.class);
                                break;
                            case SERVICEPROVIDER:
                                dbUser = dataSnapshot.getValue(ServiceProvider.class);
                                break;
                        }

                        if (password != null && dbUser.checkPassword(password)) {
                            SignInActivity.currentUser = dbUser;
                            openUserActivity(type);
                        } else {
                            Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No account with this email exists", Toast.LENGTH_SHORT).show();
                    }

                }
            };
            userObservable.subscribe(userObserver);
        });
    }

    public void openUserActivity(UserTypes types) {
        Intent intent = null;
        switch (types) {
            case HOMEOWNER:
                intent = new Intent(this, WelcomeHomeOwner.class);
                break;
            case SERVICEPROVIDER:
                intent = new Intent(this, WelcomeServiceProvider.class);
                break;
            case ADMIN:
                intent = new Intent(this, WelcomeAdministrator.class);
                break;
        }
        startActivity(intent);
    }
}
