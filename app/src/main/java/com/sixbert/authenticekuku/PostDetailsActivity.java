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


    String hisuid, ptime, myuid, uid,name, mydp, uimage, postid, plike, hisdp, hisname, dpUrl;;
    ImageView udp, image, sendComment, commenterDp, more;
    TextView uname, time, description, likeCounter, commentCounter;

    private FirebaseAuth firebaseAuth;


    LinearLayout profile;
    EditText comment;
    RecyclerView recyclerView;
    List<CommentsModel> commentModel;

    CommentsAdapter commentsAdapter;

    boolean mlike = false;
    ActionBar actionBar;
    ProgressBar progressBar;
    Button likebtn, share;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    DatabaseReference userRef;

    String phoneNumber;

    {
        assert currentUser != null;
        myuid = currentUser.getUid();
        phoneNumber = currentUser.getPhoneNumber();

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);



        //actionBar = getSupportActionBar();
        //actionBar.setTitle("Post Details");
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        postid = getIntent().getStringExtra("pid");
        recyclerView = findViewById(R.id.commentRecyclerView);
        udp = findViewById(R.id.postProfileImage);
        image = findViewById(R.id.posted_ImageView);
        uname = findViewById(R.id.nameofPoster);
        time = findViewById(R.id.timePast);
        more = findViewById(R.id.morebtn);
        //myuid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        description = findViewById(R.id.postedtText);
        commentCounter = findViewById(R.id.pcommenCounter);
        likeCounter = findViewById(R.id.plikeCounter);
        likebtn = findViewById(R.id.likeBtn);
        comment = findViewById(R.id.commentEdTxt);
        sendComment = findViewById(R.id.sendCommentBtn);
        commenterDp = findViewById(R.id.commenterImge);
        share = findViewById(R.id.shareBtn);
        profile = findViewById(R.id.profileLayoutPost);
        progressBar = new ProgressBar(this);

        loadPostInfo();
        loadUserInfo();
        setLikes();
        loadComments();

        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });
        likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likepost();
            }
        });
        likeCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailsActivity.this,
                        PostLikedByActivity.class);
                intent.putExtra("pid", postid);
                startActivity(intent);
            }
        });


    }

    private void loadComments() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        commentModel = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments");
                //child(postId);//.child("commentss");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentModel.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    CommentsModel commentsModel = dataSnapshot1.getValue(CommentsModel.class);
                    commentModel.add(commentsModel);
                    commentsAdapter = new CommentsAdapter(getApplicationContext(), commentModel);
                    recyclerView.setAdapter(commentsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setLikes() {
        final DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");

        likeRef.child(myuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ///final String postid = postModel.get(position).getUid();
                if (dataSnapshot.exists()) {

                if (dataSnapshot.hasChild(myuid)) {
                    likebtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_outlined,
                            0, 0, 0);
                    //likebtn.setText(R.string.liked);
                } else {
                    likebtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_outlined_orange,
                            0, 0, 0);
                    //likebtn.setText(R.string.like);
                }
            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void likepost() {

        mlike = true;
        final DatabaseReference likePostRef = FirebaseDatabase.getInstance().getReference("Likes");
                //.child();
        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("Posts");
               // .child("Posts");
        likePostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (mlike) {
                    if (dataSnapshot.child(postid).hasChild(myuid)) {
                        postRef.child(postid).child("likeCounter").setValue("" + (Integer.parseInt(plike) - 1));
                        likePostRef.child(postid).child(myuid).removeValue();
                        mlike = false;

                    } else {
                        postRef.child(postid).child("likeCounter").setValue("" + (Integer.parseInt(plike) + 1));
                        likePostRef.child(postid).child(myuid).setValue("Liked");
                        mlike = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void postComment() {

        final String commentss = comment.getText().toString().trim();
        if (TextUtils.isEmpty(commentss)) {
            Toast.makeText(PostDetailsActivity.this, "Hujaandika chochote tafadhali",
                    Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        final String timestamp = String.valueOf(System.currentTimeMillis());
        //long timestamp = System.currentTimeMillis();
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Comments");
        //.child(postid).child("comments");
        HashMap<String, Object> hashMap = new HashMap<>();
        //hashMap.put("cId", timestamp);
        hashMap.put("comment", commentss);
        hashMap.put("now", timestamp);
        hashMap.put("uid", myuid);
        hashMap.put("udp", dpUrl);
        hashMap.put("uname", name);
        dataRef.child(myuid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PostDetailsActivity.this, "Imeongezwa", Toast.LENGTH_LONG).show();
                comment.setText("");
                updateCommentCount();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PostDetailsActivity.this, "Haijaongezwa",
                        Toast.LENGTH_LONG).show();
            }
        });

        // userRef = FirebaseDatabase.getInstance().getReference().child("Users");


        FirebaseDatabase dbNameRef = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
       //myuid = firebaseAuth.getCurrentUser().getUid();
        userRef = dbNameRef.getReference();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("Users/" + myuid +
                        "/name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query myref = FirebaseDatabase.getInstance().getReference("Users");
        myref.orderByChild("uid").equalTo(myuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    name = dataSnapshot1.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //myuid = firebaseAuth.getCurrentUser().getUid();
        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                + firebaseAuth.getCurrentUser().getUid() + "/profile_photo.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                dpUrl = uri.toString();
            }

        });

    }

    boolean count = false;
    private void updateCommentCount() {
        count = true;
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(myuid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (count) {
                    String comments = "" + dataSnapshot.child("commentCounter").getValue();
                    int newComment = Integer.parseInt(comments) + 1;
                    reference.child("commentCounter").setValue("" + newComment);
                    count = false;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUserInfo() {

        Query myref = FirebaseDatabase.getInstance().getReference("users");
        myref.orderByChild("uid").equalTo(myuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    mydp = dataSnapshot1.child("profile_photo.jpg").getValue().toString();
                    try {
                        Glide.with(PostDetailsActivity.this).load(mydp).into(commenterDp);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = databaseReference.orderByChild("now").equalTo(postid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //String ptitle = dataSnapshot1.child("title").getValue().toString();
                    String descriptions = dataSnapshot1.child("post").getValue().toString();
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
                    //Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                    //calendar.setTimeInMillis(Long.parseLong(ptime));
                    //String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
                    //name.setText(hisname);
                    //title.setText(ptitle);
                    description.setText(descriptions);
                    likeCounter.setText(plike + R.string.likees);
                    time.setText(timeElapsed);
                    commentCounter.setText(commentcount + R.string.commcountes);
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
