package com.uottawa.mylore.fnordprojectseg2105;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        EditText txtShowUsername = (EditText) findViewById(R.id.txtShowUsername);
        TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcome);

    }
}
