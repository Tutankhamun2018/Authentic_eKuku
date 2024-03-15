package com.sixbert.authenticekuku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {


    private static String verificationID;
    private static final String KEY_VERIFICATION_ID="key_verificaition_id";
    Button btnSubmitPhone;
    Button btnVerifyOTP;
    boolean isSuccess = false;
    BillingClient billingClient;

    private FirebaseAuth mAuth;



    private EditText editPhone, editOTP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_verify_phone);
        mAuth = FirebaseAuth.getInstance();
        editPhone = findViewById(R.id.phoneNumber);
        editOTP = findViewById(R.id.edtOTPCode);
        btnSubmitPhone = findViewById(R.id.idBtnGetOtp);
        btnVerifyOTP = findViewById(R.id.btnconfirm);

        if (verificationID == null&&savedInstanceState!=null){
            onRestoreInstanceState(savedInstanceState);
        }

        /*billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build()

        //query_purchase();*/

      btnSubmitPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(editPhone.getText().toString()) || editPhone.length() != 9) {
                    Toast.makeText(VerifyPhoneActivity.this, "Tafadhali ingiza nambari ya simu", Toast.LENGTH_SHORT).show();
                    editPhone.requestFocus();
                    finish();

                } else {
                    String phone = "+255" + editPhone.getText().toString();
                    sendVerificationCode(phone);
                    //editPhone.setText("");
                }
            }
        });


        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validating if the OTP text field is empty or not
                if (TextUtils.isEmpty(editOTP.getText().toString())) {
                    //if the OTP text field is empty
                    //display a message to the user to enter OTP
                    Toast.makeText(VerifyPhoneActivity.this, "Tafadhali ingiza PIN uliyotumiwa", Toast.LENGTH_SHORT).show();

                } else {
                    //if OTP is not empty, call a method to verify it
                    verifyCode(editOTP.getText().toString());
                }
            }
        });


    }

   private void signInWithCredential(PhoneAuthCredential credential) {
        //Check if the code entered is correct or not

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if the code is correct and the task is successful
                            //we send the user to the new activity (the MainActivity)
                    //uncheck here for OTP
                            //FirebaseUser currentUser = task.getResult().getUser();

                            startActivity(new Intent(getApplicationContext(), SubscriptionsActivity.class));//replaced MainActivity.class with SubscriptionActivity
                            finish();
                    ////


                        } else {
                            //if the code is incorrect, then display an error message to the user
                            Toast.makeText(VerifyPhoneActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)		 // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)				 // Activity (for callback binding)
                        .setCallbacks(mCallBack)		 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    // callback method is called on Phone auth provider.
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationID = s;

           // Log.d("VERIFICATIONID", "VERIFY" + verificationID);
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                editOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verify code method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        try {
            // credentials from our verification id and code.

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);

        // after getting credential we are // calling sign in method.
        signInWithCredential(credential);
        }
        catch (Exception e){
            Log.i("Exception", e.toString());
            Toast.makeText(VerifyPhoneActivity.this, "Nywila huenda siyo sahihi", Toast.LENGTH_SHORT).show();
        }
    }
    //to keep the user permanently signed in
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            updateUI(currentUser);

            checkSubscription();

        }
        // [END check_current_user]
    }

    private void checkSubscription() {
        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener((billingResult, list) -> {

        }).build();
        final  BillingClient finalBillingClient = billingClient;
        billingClient.startConnection(new BillingClientStateListener(){
            @Override
            public void onBillingServiceDisconnected() {
                //checkSubscription();

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    finalBillingClient.queryPurchasesAsync(
                            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS)
                                    .build(), ((billingResult1, list) -> {
                                        if(list.size()>0){
                                            isSuccess = true;
                                            ConnectionClass.premium = true;
                                            ConnectionClass.locked = false;
                                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));//replace MainActivity with Subscriptions.Activity
                                            //finish();


                                       // } else{
                                       //     startActivity(new Intent(getApplicationContext(), SubscriptionsActivity.class));
                                        }

                            })
                    );
                }

            }


        });
    }


        private void updateUI(FirebaseUser currentUser){
            if(currentUser != null && ConnectionClass.premium){
                startActivity(new Intent(getApplicationContext(), MainActivity.class)); //take the user to subscriptionActivity
                finish();

            } else if (currentUser != null){

                startActivity(new Intent(getApplicationContext(), SubscriptionsActivity.class)); //take the user to subscriptionActivity
                finish();
            }
    }
@Override
    protected  void onSaveInstanceState(@NonNull Bundle outState){
            super.onSaveInstanceState(outState);
            outState.putString(KEY_VERIFICATION_ID, verificationID);
}

@Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
            super.onRestoreInstanceState(savedInstanceState);
            verificationID = savedInstanceState.getString(KEY_VERIFICATION_ID);
}
}





