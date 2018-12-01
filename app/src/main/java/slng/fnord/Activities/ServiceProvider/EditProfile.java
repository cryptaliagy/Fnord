package slng.fnord.Activities.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class EditProfile extends AppCompatActivity {
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedit_profile);
        final ServiceProvider serviceProvider = (ServiceProvider) SignInActivity.currentUser;

        final TextView addressView = findViewById(R.id.SPProfileAddressEditText);
        addressView.setText(serviceProvider.getAddress());

        final TextView phoneNumberView = findViewById(R.id.SPProfilePhoneNumberEditText);
        phoneNumberView.setText(serviceProvider.getPhone());

        final TextView companyNameView = findViewById(R.id.SPProfileCompanyEditText);
        companyNameView.setText(serviceProvider.getCompany());

        final TextView bioView = findViewById(R.id.SPProfileBioEditText);
        bioView.setText(serviceProvider.getBio());

        confirm = findViewById(R.id.SPProfileConfirmButton);
        confirm.setOnClickListener(view -> {
            // TODO: validation
            serviceProvider.setAddress(addressView.getText().toString());
            serviceProvider.setPhone(phoneNumberView.getText().toString());
            serviceProvider.setCompany(companyNameView.getText().toString());
            serviceProvider.setBio(bioView.getText().toString());
            new DBHelper().updateUser(serviceProvider);
            Toast.makeText(getApplicationContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
        });

    }
}
