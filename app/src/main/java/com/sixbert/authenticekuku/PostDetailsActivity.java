package com.sixbert.authenticekuku;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PostDetailsActivity extends AppCompatActivity {


    String hisuid, ptime, myuid, descriptions, name,  uimage, postID, plike, hisdp, hisname, dpUrl;;
    ImageView udp, image, btnComment, more;
    TextView uname, time, description, likeCounter, commentCounter;

    RecyclerView recyclerView;
    List<CommentsModel> commentModel;

    CommentsAdapter commentsAdapter;

    boolean mlike = false;

    ProgressBar progressBar;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    DatabaseReference userRef;


    {
        assert currentUser != null;
        myuid = currentUser.getUid();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_new_ui);

        postID = getIntent().getStringExtra("pid");
        recyclerView = findViewById(R.id.uirecyclerView);
        udp = findViewById(R.id.profileImagePostDetails);
        image = findViewById(R.id.posted_ImageView);
        uname = findViewById(R.id.nameofPoster);
        time = findViewById(R.id.timePast);
        more = findViewById(R.id.morebtn);
        description = findViewById(R.id.postedtText);
        commentCounter = findViewById(R.id.pcommenCounter);
        likeCounter = findViewById(R.id.plikeCounter);
        btnComment = findViewById(R.id.btnComment);
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //commenterDp = findViewById(R.id.commenterImge);
        //share = findViewById(R.id.shareBtn);
        //profile = findViewById(R.id.profileLayoutPost);
        progressBar = new ProgressBar(this);

        loadPostInfo();
        loadUserInfo();
        loadComments();
        //likePost();



        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent commentIntent = new Intent(PostDetailsActivity.this, CommentsActivity.class);
                startActivity(commentIntent);
            }
        });

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


    private void likePost() {


        mlike = true;
        final DatabaseReference likePostRef = FirebaseDatabase.getInstance().getReference("Likes");
                //.child();
        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("Posts").child("likesCounter");
               // .child("Posts");

        likePostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (mlike) {
                    if (dataSnapshot.child(postID).hasChild(myuid)) {
                        postRef.child(postID).child("likeCounter").setValue("" + (Integer.parseInt(plike) -1));
                        likePostRef.child(postID).child(myuid).removeValue();
                        mlike = false;

                    } else {
                        postRef.child(postID).child("likeCounter").setValue("" + (Integer.parseInt(plike) + 1));
                        likePostRef.child(postID).child(myuid).setValue("Liked");
                        mlike = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void loadUserInfo() {



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                + firebaseAuth.getCurrentUser().getUid()+"/profile_photo.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                dpUrl = uri.toString();
            }

        });
    }
    private void loadUserName() {

        Query myref = FirebaseDatabase.getInstance().getReference("Users");
        myref.orderByChild("uid").equalTo(myuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    name = dataSnapshot1.child("name").getValue().toString();

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadPostInfo() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
        //Test main path "Posts"
        Query query = databaseReference.orderByChild("now").equalTo(postID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //String ptitle = dataSnapshot1.child("title").getValue().toString();
                    descriptions = dataSnapshot1.child("post").getValue().toString();
                    uimage = dataSnapshot1.child("imageUrl").getValue().toString();
                    hisdp = dataSnapshot1.child("udp").getValue().toString();
                    hisuid = dataSnapshot1.child("uid").getValue().toString();
                    //String uemail = dataSnapshot1.child("uemail").getValue().toString();
                    hisname = dataSnapshot1.child("uname").getValue().toString();
                    ptime = dataSnapshot1.child("now").getValue().toString();
                    plike = dataSnapshot1.child("likeCounter").getValue().toString();

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

                    String commentcount = dataSnapshot1.child("commentCounter").getValue().toString();
                    description.setText(descriptions);
                    uname.setText(hisname);
                    likeCounter.setText(plike);
                    time.setText(timeElapsed);
                    commentCounter.setText(commentcount);
                    if (uimage.equals("noImage")) {
                        image.setVisibility(View.GONE);
                    } else {
                        image.setVisibility(View.VISIBLE);
                        try {
                            Glide.with(PostDetailsActivity.this).load(uimage).into(image);
                        } catch (Exception e) {
                            e.printStackTrace();

                        } /*udp = findViewById(R.id.postProfileImage);
        image = findViewById(R.id.posted_ImageView);*/
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
