package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SPViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spview_profile);
        final ServiceProvider ourSP = (ServiceProvider) SignInActivity.currentUser;

        final TextView addressView = (TextView) findViewById(R.id.viewAddress);
        addressView.setText(ourSP.getAddress());

        final TextView phoneNumberView = (TextView) findViewById(R.id.viewPhoneNumber);
        phoneNumberView.setText(ourSP.getPhone());

        final TextView companyNameView = (TextView) findViewById(R.id.viewCompanyName);
        companyNameView.setText(ourSP.getCompany());

        final TextView bioView = (TextView) findViewById(R.id.viewBio);
        bioView.setText(ourSP.getBio());

    }
}
