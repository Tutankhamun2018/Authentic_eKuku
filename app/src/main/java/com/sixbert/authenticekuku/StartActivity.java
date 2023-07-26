package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class StartActivity extends AppCompatActivity {

Button register;
//ProgressBar progressBar;
//int i =0;
//Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_start);

        register = (Button) findViewById(R.id.register);
        //login = (Button) findViewById(R.id.login);
        //progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /*final Handler handler = new Handler();
               i=progressBar.getProgress();
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       while(i<100){
                           i+=1;
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   progressBar.setProgress(i);
                               }
                           });
                           try{
                               Thread.sleep(100);
                           }catch (InterruptedException e){
                               e.printStackTrace();
                           }

                       }
                       }

               }).start();*/
                Intent intent = new Intent(StartActivity.this, VerifyPhoneActivity.class);
                startActivity(intent);
                //progressBar.setVisibility(View.GONE);
                finish(); //I don't want the user to get back, but finish
            }
        });
    //progressBar = findViewById(R.id.progressBar);
       // login.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         startActivity(new Intent(StartActivity.this, LoginActivity.class));
       //         finish();
       //     }
       // });

    }
}