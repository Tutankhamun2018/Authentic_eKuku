package com.sixbert.authenticekuku;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;



public class StartActivity extends AppCompatActivity {

    //private static final String CMD_PING_GOOGLE = "ping -c 1 google.com";
    //ProgressBar progressBarStart;
    //AppUpdateManager appUpdateManager;
    private final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
    private final String LATEST_APP_VERSION_KEY = "latest_app_version";
    public static final String KEY_UPDATE_URL = "force_update_store_url";
    String updateUrl;
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
        //progressBarStart =findViewById(R.id.progress_bar_start);

        copyrightYear = new Date();
        Calendar thisYr = Calendar.getInstance();
        copyrightYear =  thisYr.getTime();

        //progressBarStart.setVisibility(View.GONE);

        rightsReserved.setText(getResources().getString(R.string.copy_right) +" ("+""+ simpleDateFormat.format(copyrightYear)+")" );

        //checkForAppUpdate();
        //appUpdateManager = AppUpdateManagerFactory.create(this);

        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //checkInternetPingGoogle();
                //if (checkInternetPingGoogle()){
                    startActivity(new Intent(getApplicationContext(), VerifyPhoneActivity.class));//replace MainActivity with Subscriptions.Activity
                    finish();
                //} else {
                   // Toast.makeText(StartActivity.this, "Hujaunganishwa mtandaoni. Tafadhali jaribu tena", Toast.LENGTH_SHORT).show();

               // }
            }
        });
        HashMap<String, Object> firebaseDefaultMap = new HashMap<>();
        firebaseDefaultMap.put(LATEST_APP_VERSION_KEY, getCurrentVersionCode());
        remoteConfig.setDefaultsAsync(firebaseDefaultMap);
        remoteConfig.setConfigSettingsAsync(new FirebaseRemoteConfigSettings. Builder().setFetchTimeoutInSeconds(3600).build());//setDeveloperModeEnabled(true).build());
        remoteConfig.fetch().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                remoteConfig.activate();

                Log.d("TAGGED", "Fetched value "+ remoteConfig.getString(LATEST_APP_VERSION_KEY));
                checkForUpdate();

            }else {
                Toast.makeText(StartActivity.this, "Angalia Intaneti au Jaribu tena ", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private int getCurrentVersionCode(){
        try{
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        return -1;
    }

    private void checkForUpdate(){
        int fetchedVersionCode = (int) remoteConfig.getDouble(LATEST_APP_VERSION_KEY);
        if(getCurrentVersionCode()!=fetchedVersionCode) {
           new MaterialAlertDialogBuilder(this, R.style.AlertInterfaceDialogTheme).setTitle("Sasisha Upya eKuku ")
                   .setMessage("Kuna Toleo Jipya. Tafadhali sasisha na uendelee kufurahia eKuku")
                    .setPositiveButton("SAWA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            redirectStore();
                            // "Nenda PlayStore", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("Baadaye", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finish();

                        }
                    }).setCancelable(false)
                    .create()
                    .show();//.setCancelable(false).show();
        }

    }

    private void redirectStore() {
        updateUrl = remoteConfig.getString(KEY_UPDATE_URL);
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


  /*  public boolean checkInternetPingGoogle(){
        try {
            int a = Runtime.getRuntime().exec(CMD_PING_GOOGLE).waitFor();
            return a == 0x0;
        } catch (IOException | InterruptedException ioE){
            ioE.printStackTrace();
        }
        return false;
    }*/

   /* private void checkForAppUpdate(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // an activity result launcher registered via registerForActivityResult
                            AppUpdateType.IMMEDIATE, this,
                            // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                            // flexible updates.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            if(resultCode !=RESULT_OK){
                Log.w("StartActivity", "Mtiririko umefeli! Result code: " + resultCode);
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        appUpdateManager.unregisterListener(listener);
    }

    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            }
        }
    };


    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Toleo jipya la eKuku " +
                "limeshasanikishwa. ",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }*/

}