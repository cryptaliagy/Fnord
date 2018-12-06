package slng.fnord.Activities.HomeOwner;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import slng.fnord.R;
import slng.fnord.Structures.ServiceProvider;

public class ServiceProfile extends Activity {
    public static ServiceProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoservice_profile);

        TextView providerCompany = findViewById(R.id.providerProfileCompanyTV);
        TextView providerAddress = findViewById(R.id.providerProfileAddressTV);
        TextView providerPhone = findViewById(R.id.providerProfilePhoneTV);
        TextView providerBio = findViewById(R.id.providerProfileBioTV);

        RatingBar ratingBar = findViewById(R.id.serviceProfileRatingBar);

        providerCompany.setText(provider.getCompany());
        providerAddress.setText(provider.getAddress());
        providerBio.setText(provider.getBio());
        providerPhone.setText(provider.getPhone());

        ratingBar.setRating(provider.getAverageRating());

        Button book = findViewById(R.id.bookServiceButton);
        Button back = findViewById(R.id.backToSearchButton);

        book.setOnClickListener(view -> {
            BookService.serviceName = SearchProvider.serviceRequested.getServiceName();
            BookService.serviceProvider = provider;
            startActivity(new Intent(this, BookService.class));
        });

        back.setOnClickListener(view -> {
            startActivity(new Intent(this, SearchProvider.class));
        });
    }

}
