package slng.fnord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeAdministrator extends AppCompatActivity {
    private Button cserve;
    private Button dserve;
    private Button vserve;
    private Button eserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_administrator);
        //setting a custom message showing the username of the account
        TextView textView = (TextView) findViewById(R.id.welcomeAdmin);
        textView.setText("Welcome " + SignInActivity.currentUser.getUsername() + ".");

        cserve = (Button) findViewById(R.id.createServiceChoiceButton);
        cserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateServiceView();
            }
        });

        dserve = (Button) findViewById(R.id.deleteServiceChoiceButton);
        dserve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDeleteServiceView();
            }
        });

        vserve = (Button) findViewById(R.id.viewServicesButton);
        vserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewServiceView();
            }
        });

        eserve = (Button) findViewById(R.id.editServiceChoiceButton);
        eserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditServiceView();
            }
        });
    }

    public void openCreateServiceView(){
        Intent intent = new Intent(this, CreateService.class);
        startActivity(intent);
    }

    public void openDeleteServiceView(){
        Intent intent = new Intent(this, DeleteService.class);
        startActivity(intent);
    }

    public void openViewServiceView(){
        Intent intent = new Intent(this, ViewService.class);
        startActivity(intent);
    }

    public void openEditServiceView(){
        Intent intent = new Intent(this, EditServicesSelect.class);
        startActivity(intent);
    }


}
