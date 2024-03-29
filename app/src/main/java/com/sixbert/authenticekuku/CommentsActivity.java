package com.sixbert.authenticekuku;

import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;


public class CommentsActivity extends AppCompatActivity {

    final String  myuid;
    String name;
    String dpUrl;
    String postID;
    String postUid;
    ImageView  btnCommentSend, commenterDp;

    String pid = String.valueOf(System.currentTimeMillis());

    EditText comment;
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference userRef;

    final String phoneNumber;

    {
        assert currentUser != null;
        myuid = currentUser.getUid();
        phoneNumber = currentUser.getPhoneNumber();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_comments);
        postID = getIntent().getStringExtra("pid");
        postUid = getIntent().getStringExtra("postUid");

        commenterDp = findViewById(R.id.commenterImge);
        comment = findViewById(R.id.commentEdTxt);
        btnCommentSend = findViewById(R.id.sendCommentBtn);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(140);
        comment.setFilters(filters);

        btnCommentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                + firebaseAuth.getCurrentUser().getUid()+"/profile_photo.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                dpUrl = uri.toString();

                Log.d("DPURL", "dpurl" + dpUrl);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(CommentsActivity.this, "Url imeshindikana", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase dbNameRef = FirebaseDatabase.getInstance();
        userRef = dbNameRef.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("jina");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);//child("Users/" + firebaseAuth.getCurrentUser().getUid() +
                //    "/name").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void postComment() {

        final String commentss = comment.getText().toString().trim();
        if (TextUtils.isEmpty(commentss)) {
            Toast.makeText(CommentsActivity.this, "Hujaandika chochote tafadhali",
                    Toast.LENGTH_LONG).show();
            return;
        }

        final String timestamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Posts");//.child(postID).child("Comments");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("comment", commentss);
        hashMap.put("now", timestamp);
        hashMap.put("uid", myuid);
        hashMap.put("udp", dpUrl);
        hashMap.put("uname", name);

        dataRef.child(postID).child("Comments").push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CommentsActivity.this, "Imeongezwa", Toast.LENGTH_LONG).show();
                comment.setText("");
                updateCommentCount();
                //startActivity(new Intent(CommentsActivity.this, PostDetailsActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(CommentsActivity.this, "Haijaongezwa",
                        Toast.LENGTH_LONG).show();
            }
        });


    }


    boolean count = false;
    private void updateCommentCount() {
        count = true;
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts")/*.child(myuid)*/.child(postID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //for(DataSnapshot commentSnapshot:dataSnapshot.getChildren()){
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


}
