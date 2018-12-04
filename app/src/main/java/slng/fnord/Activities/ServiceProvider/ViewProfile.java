package slng.fnord.Activities.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_profile);
        final ServiceProvider ourSP = (ServiceProvider) SignInActivity.currentUser;

        final TextView addressView = findViewById(R.id.viewAddress);
        addressView.setText(ourSP.getAddress());

        final TextView phoneNumberView = findViewById(R.id.viewPhoneNumber);
        phoneNumberView.setText(ourSP.getPhone());

        final TextView companyNameView = findViewById(R.id.viewCompanyName);
        companyNameView.setText(ourSP.getCompany());

        final TextView bioView = findViewById(R.id.viewBio);
        bioView.setText(ourSP.getBio());

    }
}
