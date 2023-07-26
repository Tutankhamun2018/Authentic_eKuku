package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDetailsActivity extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;

    {
        assert currentUser != null;
        //uid = currentUser.getPhoneNumber();
        uid = currentUser.getDisplayName();
    }
    Button like_up, dislike, call, sms;
    BuyItems buyItems;
    TextView userName, likeCounter, dislikeCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        buyItems = new BuyItems();

        like_up=findViewById(R.id.likeBtn);
        dislike = findViewById(R.id.dislikeBtn);
        call = findViewById(R.id.call_user);
        sms = findViewById(R.id.msg_user);
        userName = findViewById(R.id.tvUserName);
        likeCounter = findViewById(R.id.likeCounter);
        dislikeCounter = findViewById(R.id.dislikeCounter);



        like_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + buyItems.getPhoneNumber()));
                    startActivity(intent);
                }

        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("tel:" + buyItems.getPhoneNumber()));
                startActivity(intent);

            }
        });

        userName.setText(uid);

        likeCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}