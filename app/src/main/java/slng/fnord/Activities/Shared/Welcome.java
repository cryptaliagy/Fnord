package slng.fnord.Activities.Shared;

import androidx.appcompat.app.AppCompatActivity;
import slng.fnord.Activities.Admin.ViewService;
import slng.fnord.Activities.HomeOwner.BookingList;
import slng.fnord.Activities.HomeOwner.SearchProvider;
import slng.fnord.Activities.ServiceProvider.Availability;
import slng.fnord.Activities.ServiceProvider.ViewProfile;
import slng.fnord.Activities.ServiceProvider.ViewServices;
import slng.fnord.Helpers.Enums.UserTypes;
import slng.fnord.R;
import slng.fnord.Structures.User.HomeOwner;
import slng.fnord.Structures.User.ServiceProvider;
import slng.fnord.Structures.User.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        buildActivity();

    }

    public void viewProfile(View view) {
        openActivity(ViewProfile.class);
    }

    public void viewBookings(View view) {
        if (currentUser.getType().equals(UserTypes.HOMEOWNER)) {
            openActivity(BookingList.class);
        }
    }

    public void makeBooking(View view) {
        openActivity(SearchProvider.class);
    }

    public void viewServices(View view) {
        if (currentUser.getType().equals(UserTypes.SERVICEPROVIDER)) {
            openActivity(ViewServices.class);
        } else if (currentUser.getType().equals(UserTypes.ADMIN)){
            openActivity(ViewService.class);
        }

    }

    public void viewAvailability(View view) {
        openActivity(Availability.class);
    }

    public void logOut(View view) {
        Welcome.currentUser = null;
        openActivity(MainActivity.class);
    }

    private void buildActivity() {
        UserTypes type = currentUser.getType();
        String accountType;

        switch(type) {
            case ADMIN:
                accountType = "Administrator";
                findViewById(R.id.viewServicesButton).setVisibility(View.VISIBLE);
                break;
            case SERVICEPROVIDER:
                accountType = "Service Provider";
                findViewById(R.id.viewServicesButton).setVisibility(View.VISIBLE);
                findViewById(R.id.viewProfileButton).setVisibility(View.VISIBLE);
                findViewById(R.id.availabilityButton).setVisibility(View.VISIBLE);
                //findViewById(R.id.viewBookingsButton).setVisibility(View.VISIBLE);
                break;
            case HOMEOWNER:
                accountType = "Home Owner";
                findViewById(R.id.viewProfileButton).setVisibility(View.VISIBLE);
                findViewById(R.id.makeBookingButton).setVisibility(View.VISIBLE);
                findViewById(R.id.viewBookingsButton).setVisibility(View.VISIBLE);
                break;
            default:
                accountType = "";
        }

        ((TextView) findViewById(R.id.pageTitleTV)).setText(accountType + " Page");
        if (accountType.equals("Administrator")) {
            ((TextView) findViewById(R.id.welcomeUserTV)).setText("Welcome Administrator");
        } else {
            ((TextView) findViewById(R.id.welcomeUserTV))
                    .setText("Welcome " + (accountType.equals("Home Owner") ?
                            ((HomeOwner) currentUser).getName() : ((ServiceProvider) currentUser).getCompany()));
        }
        ((TextView) findViewById(R.id.signedInAsTV)).setText("You are signed in as "+accountType);
    }

    private void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
