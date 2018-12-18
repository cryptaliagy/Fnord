package slng.fnord.Activities.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Activities.Shared.Welcome;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Helpers.Enums.UserTypes;
import slng.fnord.Managers.AccountManager;
import slng.fnord.R;
import slng.fnord.Structures.User.HomeOwner;
import slng.fnord.Structures.User.ServiceProvider;
import slng.fnord.Structures.User.User;

public class ViewProfile extends AppCompatActivity {
    private boolean firstProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_profile);

        User user = Welcome.currentUser;

        EditText addressView = findViewById(R.id.viewAddress);
        EditText phoneView = findViewById(R.id.viewPhoneNumber);
        EditText companyView = findViewById(R.id.viewCompanyName);
        EditText bioView = findViewById(R.id.viewBio);
        EditText nameView = findViewById(R.id.viewName);
        Button saveButton = findViewById(R.id.saveServiceProviderViewProfileButton);
        Button editButton = findViewById(R.id.editServiceProviderViewProfileButton);

        if (user.getType().equals(UserTypes.HOMEOWNER)) {
            buildOwnerProfile();
            nameView.setText(((HomeOwner) user).getName());
        } else {
            buildProviderProfile();
            bioView.setText(((ServiceProvider) user).getBio());
            companyView.setText(((ServiceProvider) user).getCompany());
        }

        addressView.setText(user.getAddress());
        phoneView.setText(user.getPhone());

        if ((user.getType().equals(UserTypes.HOMEOWNER)
                && ((HomeOwner) user).getName().equals(""))
                || (user.getType().equals(UserTypes.SERVICEPROVIDER))
                && ((ServiceProvider) user).getCompany().equals("")) {
            firstProfile = true;

            saveButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);

            addressView.setEnabled(true);
            phoneView.setEnabled(true);
            companyView.setEnabled(true);
            bioView.setEnabled(true);
            nameView.setEnabled(true);
        } else {
            firstProfile = false;

            addressView.setEnabled(false);
            phoneView.setEnabled(false);
            companyView.setEnabled(false);
            bioView.setEnabled(false);
            nameView.setEnabled(false);
        }
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
        EditText nameView = findViewById(R.id.viewName);

        addressView.setEnabled(true);
        phoneView.setEnabled(true);
        companyView.setEnabled(true);
        bioView.setEnabled(true);
        nameView.setEnabled(true);
        
    }

    public void saveProfile(View view) {
        Button editButton = findViewById(R.id.editServiceProviderViewProfileButton);
        Button thisButton = (Button) view;

        AccountManager manager = AccountManager.getInstance();

        EditText addressView = findViewById(R.id.viewAddress);
        EditText phoneView = findViewById(R.id.viewPhoneNumber);
        EditText companyView = findViewById(R.id.viewCompanyName);
        EditText bioView = findViewById(R.id.viewBio);
        EditText nameView = findViewById(R.id.viewName);


        String address = addressView.getText().toString();
        String phone = phoneView.getText().toString();
        String name = nameView.getText().toString();
        String company = companyView.getText().toString();
        String bio = bioView.getText().toString();

        if (companyView.getVisibility() == View.VISIBLE && !Common.validateCompany(company)) {
            companyView.setError("Invalid company name");
            return;
        }

        if (nameView.getVisibility() == View.VISIBLE && !Common.validateUser(name)) {
            nameView.setError("Invalid name");
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

        if (!firstProfile) {
            thisButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
        }
        
        addressView.setEnabled(false);
        phoneView.setEnabled(false);
        companyView.setEnabled(false);
        bioView.setEnabled(false);
        nameView.setEnabled(false);

        User user = Welcome.currentUser;

        user.setAddress(address);
        user.setPhone(phone);
        if (user.getType().equals(UserTypes.SERVICEPROVIDER)) {
            ((ServiceProvider) user).setBio(bio);
            ((ServiceProvider) user).setCompany(company);
        } else {
            ((HomeOwner) user).setName(name);
        }

        manager.updateUser(user);
        Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_SHORT).show();
        if (firstProfile) {
            startActivity(new Intent(this, Welcome.class));
        }
    }

    public void buildProviderProfile() {
        findViewById(R.id.viewAddress).setVisibility(View.VISIBLE);
        findViewById(R.id.companyAddressTextView).setVisibility(View.VISIBLE);

        findViewById(R.id.companyNameTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.viewCompanyName).setVisibility(View.VISIBLE);

        findViewById(R.id.companyBioTextField).setVisibility(View.VISIBLE);
        findViewById(R.id.viewBio).setVisibility(View.VISIBLE);

        findViewById(R.id.phoneTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.viewPhoneNumber).setVisibility(View.VISIBLE);
    }

    public void buildOwnerProfile() {
        findViewById(R.id.viewName).setVisibility(View.VISIBLE);
        findViewById(R.id.nameTextView).setVisibility(View.VISIBLE);

        findViewById(R.id.viewAddress).setVisibility(View.VISIBLE);
        findViewById(R.id.companyAddressTextView).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.companyAddressTextView)).setText("Address");

        findViewById(R.id.viewPhoneNumber).setVisibility(View.VISIBLE);
        findViewById(R.id.phoneTextView).setVisibility(View.VISIBLE);
    }



    public void back(View view) {
        if (!firstProfile) {
            startActivity(new Intent(this, Welcome.class));
        } else {
            startActivity(new Intent(this, SignInActivity.class));
        }
    }

}
