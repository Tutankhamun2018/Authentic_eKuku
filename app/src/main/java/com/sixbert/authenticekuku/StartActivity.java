package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

Button register;
//Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        register = (Button) findViewById(R.id.register);
        //login = (Button) findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, VerifyPhoneActivity.class));
                finish(); //I don't want the user to get back, but finish
            }
        });

       // login.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         startActivity(new Intent(StartActivity.this, LoginActivity.class));
       //         finish();
       //     }
       // });

    }
}