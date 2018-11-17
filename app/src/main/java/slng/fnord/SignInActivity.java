package slng.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import io.reactivex.Observable;

public class SignInActivity extends AppCompatActivity {
    private Button signIn2;
    String email;
    String password;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn2 = (Button) findViewById(R.id.signInButton);
        final SignInActivity act = this;
        signIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = (EditText) findViewById(R.id.signInEmail);
                EditText passwordText = (EditText) findViewById(R.id.signInPassword);

                Accounts acc = MainActivity.getAccounts();
                User user = null;

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

                Observable<DataSnapshot> userObservable = DBHelper.makeObservableFromPath("users/"+id);
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

                            if (dbUser.checkPassword(password)) {
                                SignInActivity.currentUser = dbUser;
                                openUserActivity(type);
                            } else {
                                Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                };

                userObservable.subscribe(userObserver);
            }
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
