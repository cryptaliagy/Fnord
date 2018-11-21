package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SPEditProfile extends AppCompatActivity {
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedit_profile);
        final ServiceProvider serviceProvider = (ServiceProvider) SignInActivity.currentUser;

        final TextView addressView = (TextView) findViewById(R.id.SPProfileAddressEditText);
        addressView.setText(serviceProvider.getAddress());

        final TextView phoneNumberView = (TextView) findViewById(R.id.SPProfilePhoneNumberEditText);
        phoneNumberView.setText(serviceProvider.getPhone());

        final TextView companyNameView = (TextView) findViewById(R.id.SPProfileCompanyEditText);
        companyNameView.setText(serviceProvider.getCompany());

        final TextView bioView = (TextView) findViewById(R.id.SPProfileBioEditText);
        bioView.setText(serviceProvider.getBio());

        confirm = (Button) findViewById(R.id.SPProfileConfirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will need validations here - will add them once I confirm this works
                serviceProvider.setAddress(addressView.getText().toString());
                serviceProvider.setPhone(phoneNumberView.getText().toString());
                serviceProvider.setCompany(companyNameView.getText().toString());
                serviceProvider.setBio(bioView.getText().toString());
                DBHelper.updateUser(serviceProvider);
                Toast.makeText(getApplicationContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
