package com.sixbert.authenticekuku;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    Context context;
    ImageView imageView, gallery, sendPost ;
    String TAG = "URL";
    //String downloadUrl;

    HashMap<String, Object> map;

    private EditText sendText;
    private Uri imageUri = null;

    String dpUrl;
    String name;

    StorageReference storageReference;
    private DatabaseReference userRef;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
    String uid;
    String phoneNumber;

    {
        assert currentUser != null;
        uid = currentUser.getUid();
        phoneNumber = currentUser.getPhoneNumber();

    }


    ArrayAdapter<String> adapter;

    public PostActivity (){

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_post);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        imageView = findViewById(R.id.imagePreView);
        gallery = findViewById(R.id.gallery);
        //attachPhoto = findViewById(R.id.attachPhoto);
        sendText = findViewById(R.id.sendText);
        sendPost = findViewById(R.id.sendPost);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(200);
        sendText.setFilters(filters);

        //get the image from the gallery

        ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            assert result.getData() != null;
                            imageUri = result.getData().getData();
                            imageView.setImageURI(imageUri);
                        }

                    }


                });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(intent);
            }
        });


        sendPost.setOnClickListener(view -> {

            String txt_post = sendText.getText().toString();

            if (txt_post.trim().isEmpty()) {
                Toast.makeText(PostActivity.this, "Hujaandika kitu", Toast.LENGTH_SHORT).show();
            } else {
                addDataToFirebase(txt_post);

            }
        });

    }


    private void addDataToFirebase(final String txt_post) {
        if (imageUri != null) {
            Bitmap bmp;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ////
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String uid = firebaseAuth.getCurrentUser().getUid();
            StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                    + firebaseAuth.getCurrentUser().getUid()+"/profile_photo.jpg");

            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    dpUrl = uri.toString();
                }

            });//

            FirebaseDatabase dbNameRef = FirebaseDatabase.getInstance();
            userRef = dbNameRef.getReference();
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.child("Users/" + firebaseAuth.getCurrentUser().getUid() +
                            "/name").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            /*FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String uid = firebaseAuth.getCurrentUser().getUid();
            StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                    + firebaseAuth.getCurrentUser().getUid()+"/profile_photo.jpg");

            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    dpUrl = uri.toString();

                }*/

            //String uid = firebaseAuth.getCurrentUser().getUid();
            storageReference = FirebaseStorage.getInstance().getReference().child("camera_photo/"
                    + firebaseAuth.getCurrentUser().getUid()+"/post_images.jpg");

            //StorageReference ref = storageReference.child("post_images.jpg/");//added path to save images from users


            final String now = String.valueOf(System.currentTimeMillis());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] data = baos.toByteArray();

            storageReference.putBytes(data).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;

                                    String downloadUrl = uriTask.getResult().toString();
                                    if (uriTask.isSuccessful()) {

                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("phoneNumber", phoneNumber);
                                        map.put("now", now); //Date timestamp.. changed from now to timeStamp
                                        map.put("post", txt_post);
                                        map.put("imageUrl", downloadUrl);
                                        map.put("likeCounter", "0");
                                        map.put("commentCounter", "0");
                                        map.put("uid", uid);
                                        map.put("uname", name);
                                        map.put("udp", dpUrl);
                                        //postRef.push().setValue(map);

                                        DatabaseReference postRef = fireDB.getReference("Posts");

                                        postRef.child(uid).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {

                                            @Override

                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(PostActivity.this, "Imeongezwa kikamilifu", Toast.LENGTH_SHORT).show();
                                                sendText.setText("");
                                                imageView.setImageURI(null);
                                                imageView.setVisibility(View.GONE);
                                                startActivity(new Intent(PostActivity.this, PostNewsActivity.class));
                                                finish();


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });


                                    }


                                }


                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(PostActivity.this, "Picha haijapandishwa" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() /
                                    snapshot.getTotalByteCount());

                            //progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(PostActivity.this, "Inapakia " + (int) progress + "%", Toast.LENGTH_LONG).show();

                        }
                    });
        }




        }
    }






