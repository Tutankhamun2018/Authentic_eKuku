package com.sixbert.authenticekuku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private static final String TAG = "VerifyPhoneActivity";
    //String for storing our verificationID
    private String verificationID;
    //variable for Firebase Auth Class
    private FirebaseAuth mAuth;
    //ProgressBar
    private ProgressBar progressBar;
    private CheckBox checkBox;
    //Text input field variables
    private EditText editPhone, editOTP;
    //buttons for submitting phone and verifying OTP
    private Button btnSubmitPhone, btnVerifyOTP;
    //private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        mAuth = FirebaseAuth.getInstance();
        //progressBar = findViewById(R.id.progressbar);
        editPhone = findViewById(R.id.phoneNumber);
        checkBox = findViewById(R.id.chkBox);
        editOTP = findViewById(R.id.edtOTPCode);
        btnSubmitPhone = findViewById(R.id.idBtnGetOtp);
        btnVerifyOTP = findViewById(R.id.btnconfirm);
        mytoolbar = findViewById(R.id.verifyToolbar);
        setSupportActionBar(mytoolbar);



        btnSubmitPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean chckbox = checkBox.isChecked();
                //the belo code checks if the user has entered a
                // correct phone number in the editText field
                if (TextUtils.isEmpty(editPhone.getText().toString()) || editPhone.length() < 10) {
                    Toast.makeText(VerifyPhoneActivity.this, "Tafadhali ingiza nambarri ya simu", Toast.LENGTH_SHORT).show();
                    editPhone.requestFocus();
                    finish();
                } else if (!chckbox) {
                    //chckbox.setError("Kubali vigezo na masharti kwanza")
                    Toast.makeText(VerifyPhoneActivity.this, "Kubali kwanza vigezo na masharti", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String phone = "+255" + editPhone.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });

        //initialize on click listener to verify OTPbutton

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

    //private void verifyCode(String code) {
     //   PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
    //    signInWithCredential(credential);
    //}


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

                            Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                    ////


                        } else {
                            //if the code is incorrect, then display an error message to the user
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
    //to keep the user permanently signed in
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            updateUI(currentUser);

        }
        // [END check_current_user]
    }

    private void updateUI(FirebaseUser currentUser){
        if(currentUser !=null){

            Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
}




