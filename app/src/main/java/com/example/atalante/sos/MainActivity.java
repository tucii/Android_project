package com.example.atalante.sos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText passwordInput;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);



    }
    public void login (View view){

            SharedPreferences loginPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = loginPreferences.edit();


    if (usernameInput.length() > 5 && passwordInput.length() > 5){

            editor.putString("username", usernameInput.getText().toString());
            editor.putString("password", passwordInput.getText().toString());
            editor.apply();
            Toast.makeText(this, "Login!", Toast.LENGTH_LONG).show();

        Intent i = new Intent(MainActivity.this, Contact.class);
        startActivity(i);
    }
    else {
        Toast.makeText(this, "Username and Password must be contain min 5 character. ", Toast.LENGTH_SHORT).show();

    }
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        String username = usernameInput.getText().toString();

        Intent intentinfo = new Intent(view.getContext(),UserInformation.class);
       intentinfo.putExtra("username",username);
        startActivity(intentinfo);
    }



}

