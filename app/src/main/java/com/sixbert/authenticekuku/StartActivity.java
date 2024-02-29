package com.sixbert.authenticekuku;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class StartActivity extends AppCompatActivity {

    private static final String CMD_PING_GOOGLE = "ping -c 1 google.com";
    ProgressBar progressBarStart;

    Button startBtn;
    Date copyrightYear;
    TextView rightsReserved;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.getDefault(Locale.Category.FORMAT));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_start);
        startBtn = findViewById(R.id.register);
        rightsReserved = findViewById(R.id.rightsReserved);
        progressBarStart =findViewById(R.id.progress_bar_start);

        copyrightYear = new Date();
        Calendar thisYr = Calendar.getInstance();
        copyrightYear =  thisYr.getTime();

        progressBarStart.setVisibility(View.GONE);

        rightsReserved.setText(getResources().getString(R.string.copy_right) +" ("+"DASG "+ simpleDateFormat.format(copyrightYear)+")" );

        checkInternetPingGoogle();

        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //checkInternetPingGoogle();
                if (checkInternetPingGoogle()){
                    startActivity(new Intent(getApplicationContext(), VerifyPhoneActivity.class));//replace MainActivity with Subscriptions.Activity
                    finish();
                } else {
                    Toast.makeText(StartActivity.this, "Hujaunganishwa mtandaoni. Tafadhali jaribu tena", Toast.LENGTH_SHORT).show();

                }
            }
        });

       /* if (checkInternetPingGoogle()){
            startActivity(new Intent(getApplicationContext(), VerifyPhoneActivity.class));//replace MainActivity with Subscriptions.Activity
            finish();
        } else {
            Toast.makeText(StartActivity.this, "Hujaunganishwa mtandaoni. Tafadhali jaribu tena", Toast.LENGTH_SHORT).show();

        }*/
    }


    public boolean checkInternetPingGoogle(){
        try {
            int a = Runtime.getRuntime().exec(CMD_PING_GOOGLE).waitFor();
            return a == 0x0;
        } catch (IOException | InterruptedException ioE){
            ioE.printStackTrace();
        }
        return false;
    }

}