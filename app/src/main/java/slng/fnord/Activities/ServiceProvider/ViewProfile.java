package slng.fnord.Activities.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Managers.AccountManager;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_profile);
        final ServiceProvider ourSP = (ServiceProvider) SignInActivity.currentUser;

        EditText addressView = findViewById(R.id.viewAddress);
        addressView.setText(ourSP.getAddress());

        EditText phoneNumberView = findViewById(R.id.viewPhoneNumber);
        phoneNumberView.setText(ourSP.getPhone());

        EditText companyNameView = findViewById(R.id.viewCompanyName);
        companyNameView.setText(ourSP.getCompany());

        EditText bioView = findViewById(R.id.viewBio);
        bioView.setText(ourSP.getBio());

        addressView.setEnabled(false);
        phoneNumberView.setEnabled(false);
        companyNameView.setEnabled(false);
        bioView.setEnabled(false);

    }

    public void editProfile(View view) {
        Button saveButton = findViewById(R.id.saveServiceProviderViewProfileButton);
        Button thisButton = (Button) view;

        thisButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);

        EditText addressView = findViewById(R.id.viewAddress);
        EditText phoneView = findViewById(R.id.viewPhoneNumber);
        EditText companyView = findViewById(R.id.viewCompanyName);
        EditText bioView = findViewById(R.id.viewBio);

        addressView.setEnabled(true);
        phoneView.setEnabled(true);
        companyView.setEnabled(true);
        bioView.setEnabled(true);
        
    }

    public void saveProfile(View view) {
        Button editButton = findViewById(R.id.editServiceProviderViewProfileButton);
        Button thisButton = (Button) view;

        AccountManager manager = new AccountManager(new DBHelper());

        EditText addressView = findViewById(R.id.viewAddress);
        EditText phoneView = findViewById(R.id.viewPhoneNumber);
        EditText companyView = findViewById(R.id.viewCompanyName);
        EditText bioView = findViewById(R.id.viewBio);


        String address = addressView.getText().toString();
        String phone = phoneView.getText().toString();
        String company = companyView.getText().toString();
        String bio = bioView.getText().toString();

        if (!Common.validateCompany(company)) {
            companyView.setError("Invalid company name");
            return;
        }

        if (!Common.validateAddress(address)) {
            addressView.setError("Invalid address");
            return;
        }

        if (!Common.validatePhoneNumber(phone)) {
            phoneView.setError("Invalid phone number");
            return;
        }

        thisButton.setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
        
        addressView.setEnabled(false);
        phoneView.setEnabled(false);
        companyView.setEnabled(false);
        bioView.setEnabled(false);

        ServiceProvider user = (ServiceProvider) SignInActivity.currentUser;

        user.setCompany(company);
        user.setAddress(address);
        user.setPhone(phone);
        user.setBio(bio);

        manager.updateUser(user);
        Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        startActivity(new Intent(this, Welcome.class));
    }

}
