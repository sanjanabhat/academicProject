package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class signupadmin extends AppCompatActivity {
    EditText usernameField, passwordField;
    ImageView logo;
    Button signUpButton;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupadmin);
        getSupportActionBar().setTitle("Add Admin");

        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        logo = (ImageView) findViewById(R.id.logo);
        signUpButton.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {



                                                ParseUser user = new ParseUser();
                                                user.setUsername(String.valueOf(usernameField.getText()));
                                                user.setPassword(String.valueOf(passwordField.getText()));

                                                user.signUpInBackground(new SignUpCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if (e == null) {

                                                            Log.i("AppInfo", "Signup Successful");
                                                            addtolist();


                                                        }
                                                    }
                                                });



                                                ParseAnalytics.trackAppOpenedInBackground(getIntent());

                                            }
                                        }
        );
    }
    void addtolist()
    {
        ParseObject admin= new ParseObject("Admin");
        admin.put("username",String.valueOf(usernameField.getText()));
        admin.saveInBackground();
        Intent i = new Intent(getApplicationContext(), UserListAdmin.class);
        startActivity(i);

    }
}


