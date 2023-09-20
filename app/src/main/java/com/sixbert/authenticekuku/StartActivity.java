package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;


public class StartActivity extends AppCompatActivity {

    Button register;
    ProgressBar progressBar;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_start);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progress_bar_start);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                setProgressValue(i);
                Intent intent = new Intent(StartActivity.this, VerifyPhoneActivity.class);
                startActivity(intent);
                finish(); //I don't want the user to get back, but finish
            }
        });


    }

    private void setProgressValue(final int i) {

        // set the progress
        progressBar.setProgress(i);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(i + 10);
            }
        });
        thread.start();
    }
}