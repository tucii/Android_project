package com.example.atalante.sos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserInformation extends AppCompatActivity {

    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        username = (TextView) findViewById(R.id.username);
        username.setText(getIntent().getStringExtra("username"));


    }
}
