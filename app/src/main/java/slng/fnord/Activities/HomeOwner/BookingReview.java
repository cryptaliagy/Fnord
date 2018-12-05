package slng.fnord.Activities.HomeOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import slng.fnord.Activities.ServiceProvider.Availability;
import slng.fnord.Activities.Shared.SignInActivity;
import slng.fnord.Activities.Shared.Welcome;
import slng.fnord.Database.DBHelper;
import slng.fnord.Managers.AccountManager;
import slng.fnord.Managers.ServicesManager;
import slng.fnord.R;
import slng.fnord.Structures.Booking;
import slng.fnord.Structures.Ratings;
import slng.fnord.Structures.ServiceProvider;

public class BookingReview extends AppCompatActivity {
    private Button addService;
    private String commentToAdd;
    private String raterName;

    private AccountManager accountManager;
    private Button back;

    public static Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobooking_review);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.BRRatingBar);

        accountManager = new AccountManager(new DBHelper());
        ServicesManager servicesManager = new ServicesManager(new DBHelper());

        TextView SPCompanyName = findViewById(R.id.BRCompanyNameTV);
        SPCompanyName.setText(booking.getServiceProviderInfo().getCompany());

        TextView ServiceRequested = findViewById(R.id.BRServiceRequestedTV);
        ServiceRequested.setText(booking.getService());

        TextView DayOfService = findViewById(R.id.BRDOSTV);
        Calendar cal = booking.dateAsCalendar();
        SimpleDateFormat theDate = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.US);
        String strTheDate = theDate.format(cal.getTime());
        DayOfService.setText(strTheDate);



        TextView TimeOfDay = findViewById(R.id.BRTODTV);
        TimeOfDay.setText(booking.getStartTime()+":00");

        EditText ratingCommentBox = findViewById(R.id.BRCommentPanel);
        raterName = Welcome.currentUser.getEmail();

        addService = findViewById(R.id.BRUpdateReviewButton);
        addService.setOnClickListener(view -> {
            commentToAdd = ratingCommentBox.getText().toString();
            Ratings ratingToAdd = new Ratings((int) ratingBar.getRating(), commentToAdd, raterName);
            String providerEmail = booking.getServiceProviderInfo().getEmail();
            accountManager.getUser(providerEmail, optionalUser -> {
                    ServiceProvider user = (ServiceProvider) optionalUser;
                    user.addRating(ratingToAdd);
                    accountManager.updateUser(user);
                    Toast.makeText(getApplicationContext(), "Your review has been successfully updated!", Toast.LENGTH_SHORT).show();
                    openView(Welcome.class);

            });
        });

        back = findViewById(R.id.backToBookingsButton);
        back.setOnClickListener(view -> openView(BookingList.class));

    }

    public void openView(final Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

}