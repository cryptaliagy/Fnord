package slng.fnord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SPEditProfile extends AppCompatActivity {
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedit_profile);
        final ServiceProvider ourSP = (ServiceProvider) SignInActivity.currentUser;

        final TextView addressView = (TextView) findViewById(R.id.SPProfileAddressEditText);
        addressView.setText(ourSP.getSPAddress());

        final TextView phoneNumberView = (TextView) findViewById(R.id.SPProfilePhoneNumberEditText);
        phoneNumberView.setText(ourSP.getSPPhoneNumber());

        final TextView companyNameView = (TextView) findViewById(R.id.SPProfileCompanyEditText);
        companyNameView.setText(ourSP.getSPCompanyName());

        final TextView bioView = (TextView) findViewById(R.id.SPProfileBioEditText);
        bioView.setText(ourSP.getSPBiography());

        confirm = (Button) findViewById(R.id.SPProfileConfirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will need validations here - will add them once I confirm this works
                ourSP.setSPAddress(addressView.getText().toString());
                ourSP.setSPPhoneNumber(phoneNumberView.getText().toString());
                ourSP.setSPCompanyName(companyNameView.getText().toString());
                ourSP.setSPBiography(bioView.getText().toString());
            }
        });

    }
}
