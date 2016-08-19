package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Finalpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalpage);

        //ParseUser.logOut();
        Intent back=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(back);
    }
}
