package com.sixbert.authenticekuku;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostDetailsActivity extends AppCompatActivity {


    String hisuid, ptime, myuid, descriptions, uimage, postID, plike, hisdp, hisname, dpUrl;
    ImageView udp, image, btnComment;

    TextView uname, time, description, likeCounter, commentCounter;


    RecyclerView recyclerView;
    List<CommentsModel> commentModel;

    CommentsAdapter commentsAdapter;


    ProgressBar progressBar;

    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    {
        assert currentUser != null;
        myuid = currentUser.getUid();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_post_details_new_ui);

        postID = getIntent().getStringExtra("pid");
        recyclerView = findViewById(R.id.uirecyclerView);
        udp = findViewById(R.id.profileImagePostDetails);
        image = findViewById(R.id.posted_ImageView);
        uname = findViewById(R.id.nameofPoster);
        time = findViewById(R.id.timePast);
        description = findViewById(R.id.postedtText);
        commentCounter = findViewById(R.id.pcommenCounter);
        likeCounter = findViewById(R.id.plikeCounter);
        btnComment = findViewById(R.id.btnComment);
        myuid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        progressBar = new ProgressBar(this);




        loadPostInfo();
        loadUserInfo();
        loadComments();
        //likePost();


    }

    private void loadComments() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        commentModel = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").
                child(postID).child("Comments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentModel.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        CommentsModel commentsModel = dataSnapshot1.getValue(CommentsModel.class);
                        commentModel.add(commentsModel);
                        commentsAdapter = new CommentsAdapter(getApplicationContext(), commentModel, myuid, postID);
                        recyclerView.setAdapter(commentsAdapter);
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void loadUserInfo() {



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()+"/profile_photo.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                dpUrl = uri.toString();
            }

        });
    }

    private void loadPostInfo() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");//.child(postID);
        Query query = databaseReference.orderByChild("now").equalTo(postID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        descriptions = Objects.requireNonNull(dataSnapshot1.child("post").getValue()).toString();
                        if (!dataSnapshot1.child("imageUrl").exists()){

                            uimage = null;
                        }
                        else{
                            uimage = Objects.requireNonNull(dataSnapshot1.child("imageUrl").getValue()).toString();
                        }
                        if(!dataSnapshot1.child("udp").exists()){
                            hisdp =null;
                        } else{
                            hisdp = Objects.requireNonNull(dataSnapshot1.child("udp").getValue()).toString();
                        }

                        hisuid = Objects.requireNonNull(dataSnapshot1.child("uid").getValue()).toString();
                    if(!dataSnapshot1.child("uname").exists()){
                        hisname =null;
                    } else{
                        hisname = Objects.requireNonNull(dataSnapshot1.child("uname").getValue()).toString();
                    }

                        ptime = Objects.requireNonNull(dataSnapshot1.child("now").getValue()).toString();
                        plike = Objects.requireNonNull(dataSnapshot1.child("likeCounter").getValue()).toString();

                        long currentTime = System.currentTimeMillis();
                        long serverTme = Long.parseLong(ptime);

                        long elapsedTime = currentTime - serverTme;
                        long seconds = elapsedTime / 1000;
                        long minutes = seconds / 60;
                        long hours = minutes / 60;
                        long days = hours / 24;

                        String timeElapsed;
                        if (days > 0) {
                            timeElapsed = days + " days ago";
                        } else if (hours > 0) {
                            timeElapsed = hours + " hrs ago";
                        } else if (minutes > 0) {
                            timeElapsed = minutes + " mins ago";
                        } else {
                            timeElapsed = seconds + " secs ago";
                        }

                        String commentcount = Objects.requireNonNull(dataSnapshot1.child("commentCounter").getValue()).toString();
                        description.setText(descriptions);
                        uname.setText(hisname);
                        likeCounter.setText(plike);
                        time.setText(timeElapsed);
                        commentCounter.setText(commentcount);
                        if (uimage == null) {
                            image.setVisibility(View.GONE);
                        } else {
                            image.setVisibility(View.VISIBLE);
                            try {
                                Glide.with(PostDetailsActivity.this).load(uimage).into(image);

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                        try {
                            Glide.with(PostDetailsActivity.this).load(hisdp).into(udp);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
