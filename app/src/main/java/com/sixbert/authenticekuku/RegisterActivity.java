package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText phone;
    CheckBox checkBox;
    Button register;
    TextView tandC;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phone = findViewById(R.id.phoneNumber);
        tandC = findViewById(R.id.tvPrivacyPolicy);
        checkBox = findViewById(R.id.chkBox);
        register = findViewById(R.id.btngetOTP);

        //confirmOTP = findViewById(R.id.btnConfirmOTP);
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        /*whenever the register button is clicked
        we need to check if some values are entered correctly and then register
         GoTo Tools>Firebase>Authentication>custom authentication
        >'Add the Firebase Authentication SDK to your app' button
        this will be reflected in Gradle App level*/
        //register the user now by setting onclickListener

        register.setOnClickListener(view -> {
            //get the texts entered in email and password fields

              //  String txtPhoneNo = "+255"+ phone.getText().toString().trim();


            //String txtPassword = password.getText() + "";
            boolean chckbox = checkBox.isChecked();
            //crosscheck if the fields are not empty by using TextUtil method()

            if (phone.length() < 10) {
                phone.setError("Nambari ya simu inahitajika");
                //Toast.makeText(RegisterActivity.this, "Please add a valid phone Number", Toast.LENGTH_SHORT).show();
                phone.requestFocus();
                return;
            } else if (!chckbox) {
                //chckbox.setError("Kubali vigezo na masharti kwanza")
                Toast.makeText(RegisterActivity.this, "Kubali kwanza vigezo na masharti", Toast.LENGTH_SHORT).show();
                return;

            } else if (phone.getText().toString().equals("")){
                phone.setError("Jaza nambari ya simu");
            }


            Intent intent = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
            String phoneNumber = phone.getText().toString();
            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
        });

        tandC.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, TermsActivity.class)));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}


