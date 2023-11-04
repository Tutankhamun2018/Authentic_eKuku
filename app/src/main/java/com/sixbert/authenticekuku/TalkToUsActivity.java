package com.sixbert.authenticekuku;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TalkToUsActivity extends AppCompatActivity {
Button button;

EditText subject, body;
TextView address;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


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
        drawerLayout = findViewById(R.id.drawer_layout_ttus);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        button.setOnClickListener(view -> {
                    String emailSend = address.getText().toString();
                    String emailSubject = subject.getText().toString();
                    String emailBody = body.getText().toString();



                if (emailSubject.trim().isEmpty() || emailSend.trim().isEmpty() || emailBody.trim().isEmpty()) {
                    Toast.makeText(TalkToUsActivity.this, "Jaza kikamilifu tafadhali", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(Intent.ACTION_SEND);

                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailSend});
                    intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                    intent.putExtra(Intent.EXTRA_TEXT, emailBody);

                    intent.setType("message/rfc822");

                    startActivity(Intent.createChooser(intent, "Choose an Email Client :"));
                }
finish();

        });


    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        //finish();
        startActivity(intent);
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        finish();
    }
}