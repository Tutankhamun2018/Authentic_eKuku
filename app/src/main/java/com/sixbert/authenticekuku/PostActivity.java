package com.sixbert.authenticekuku;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {
    ImageView imageView, gallery, sendPost;

    private final int i =0;
    final long photoTime = System.currentTimeMillis();

    private EditText sendText;
    private Uri imageUri = null;

    String dpUrl, postID, name;
    ProgressBar progressBar;


    StorageReference storageReference;
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    final FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
    final String uid;
    final String postUid;
    final String phoneNumber;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    {
        assert currentUser != null;
        uid = currentUser.getUid();
        postUid = currentUser.getUid();
        phoneNumber = currentUser.getPhoneNumber();

    }

    public PostActivity (){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_post);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postID = getIntent().getStringExtra("pid");

        imageView = findViewById(R.id.imagePreView);
        gallery = findViewById(R.id.gallery);
        progressBar = findViewById(R.id.progressbarnewPost);
        sendText = findViewById(R.id.sendText);
        sendPost = findViewById(R.id.sendPost);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(200);
        sendText.setFilters(filters);


        ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        assert result.getData() != null;
                        imageUri = result.getData().getData();
                        imageView.setImageURI(imageUri);
                    }

                });

        gallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryActivityResultLauncher.launch(intent);

        });


        sendPost.setOnClickListener(view -> {

            String txt_post = sendText.getText().toString();

            if (txt_post.trim().isEmpty()) {
                Toast.makeText(PostActivity.this, "Hujaandika kitu", Toast.LENGTH_SHORT).show();
            } else {

                progressBar.setVisibility(View.VISIBLE);

                setProgressValue(i);

                addDataToFirebase(txt_post);

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                + firebaseAuth.getCurrentUser().getUid() + "/profile_photo.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                dpUrl = uri.toString();
            }

        });

        FirebaseDatabase dbNameRef = FirebaseDatabase.getInstance();
        userRef = dbNameRef.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("jina");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void addDataToFirebase(final String txt_post) {
        Bitmap bmp = null;
        if (imageUri != null) {
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            storageReference = FirebaseStorage.getInstance().getReference().child("camera_photo/"
                    + firebaseAuth.getCurrentUser().getUid() + "/" + photoTime);


            final String now = String.valueOf(System.currentTimeMillis());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] data = baos.toByteArray();

            storageReference.putBytes(data).addOnSuccessListener(
                            taskSnapshot -> {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;

                                String downloadUrl = uriTask.getResult().toString();
                                if (uriTask.isSuccessful()) {

                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("phoneNumber", phoneNumber);
                                    map.put("now", now);
                                    map.put("post", txt_post);
                                    map.put("imageUrl", downloadUrl);
                                    map.put("likeCounter", "0");
                                    map.put("commentCounter", "0");
                                    map.put("uid", uid);
                                    map.put("uname", name);
                                    map.put("udp", dpUrl);


                                    DatabaseReference postRef = fireDB.getReference("Posts").child(now);//replaced "now"


                                    postRef.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {

                                        @Override

                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(PostActivity.this, "Imeongezwa kikamilifu", Toast.LENGTH_SHORT).show();
                                            sendText.setText("");
                                            imageView.setImageURI(null);
                                            progressBar.setVisibility(View.GONE);
                                            imageView.setVisibility(View.GONE);
                                            startActivity(new Intent(PostActivity.this, PostNewsActivity.class));
                                            finish();


                                        }
                                    }).addOnFailureListener(e -> {

                                    });


                                }


                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(PostActivity.this, "Picha haijapandishwa" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnProgressListener(snapshot -> {
                        double progress = (100.0 * snapshot.getBytesTransferred() /
                                snapshot.getTotalByteCount());

                        Toast.makeText(PostActivity.this, "Inapakia " + (int) progress + "%", Toast.LENGTH_LONG).show();

                    });
        } else {


            final String now = String.valueOf(System.currentTimeMillis());
            DatabaseReference postnopicRef = fireDB.getReference("Posts").child(now);//replaced "now"
            HashMap<String, Object> newMap = new HashMap<>();
            newMap.put("phoneNumber", phoneNumber);
            newMap.put("now", now);
            newMap.put("post", txt_post);
            newMap.put("likeCounter", "0");
            newMap.put("commentCounter", "0");
            newMap.put("uid", uid);
            newMap.put("uname", name);
            newMap.put("udp", dpUrl);

            postnopicRef.setValue(newMap).addOnSuccessListener(new OnSuccessListener<Void>() {

                @Override

                public void onSuccess(Void aVoid) {
                    Toast.makeText(PostActivity.this, "Imeongezwa kikamilifu", Toast.LENGTH_SHORT).show();
                    sendText.setText("");
                    imageView.setImageURI(null);
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(PostActivity.this, PostNewsActivity.class));
                    finish();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, "Haijaongezwa",
                            Toast.LENGTH_LONG).show();
                }
            });


        }

        }





            private void setProgressValue ( final int i){

                progressBar.setProgress(i);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setProgressValue(i + 10);
                    }
                });
                thread.start();
            }


        }






