package com.sixbert.authenticekuku;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TalkToUsActivity extends AppCompatActivity {
Button button;

EditText address, subject, body;


    public TalkToUsActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_talk_to_us);


        address = findViewById(R.id.edtEmailAddress);
        subject = findViewById(R.id.emailSubject);
        body= findViewById(R.id.mailbody);
        button = findViewById(R.id.sendmail);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailSend = address.getText().toString();
                String emailSubject = subject.getText().toString();
                String emailBody = body.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailSend});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailBody);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Choose an Email Client :"));


            }

        });


    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}