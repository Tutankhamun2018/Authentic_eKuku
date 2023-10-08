package com.sixbert.authenticekuku;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;

    StorageReference profileRef;

    String uid;

    ImageView set;
    TextView profilepic, uploadprofilepic;
    ProgressBar progressBar;

    Uri imageUri;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    {
        assert currentUser != null;
        uid = currentUser.getUid();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        profilepic = findViewById(R.id.profilepic);

        set = findViewById(R.id.setting_profile_image);
        uploadprofilepic = findViewById(R.id.uploadprofilepic);
        progressBar = new ProgressBar(this);
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();

        profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                +firebaseAuth.getCurrentUser().getUid()+"/profile_photo.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri.toString()).into(set);
            }
        });



        ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        assert result.getData() != null;
                        imageUri = result.getData().getData();
                        set.setImageURI(imageUri);

                        //uploadProfileCoverPhoto(imageUri);
                    }

                });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(intent);

            }


        });

        uploadprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileCoverPhoto(imageUri);
            }
        });

    }

    // We will upload the image from here.
    private void uploadProfileCoverPhoto(final Uri imageUri) {
        //progressBar.setVisibility(View.VISIBLE);
        if (imageUri != null) {

            StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("users/"
                    + firebaseAuth.getCurrentUser().getUid()+"/profile_photo.jpg");
            storageReference1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getApplicationContext()).load(uri.toString()).into(set);
                        }//Glide.with(holder.itemView.getContext()).load(postModel.get(position).getImageUrl()).into(holder.imageView);
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //pd.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Haijabadilisha ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    });
                }
            });
                } else {
                    //pd.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }


        }




