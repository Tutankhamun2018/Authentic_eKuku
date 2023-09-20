package com.sixbert.authenticekuku;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditNameActivity extends AppCompatActivity {

    TextView change;
    ProgressBar progressBar;
    FirebaseDatabase fbDB;
    DatabaseReference dbRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String uid;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        firebaseAuth = FirebaseAuth.getInstance();
        fbDB = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        change = findViewById(R.id.change_name);
        progressBar = new ProgressBar(this);


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName("name");
            }
        });


    }



    private void changeName(final String key) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Update" + key);

            // creating a layout to write the new name
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(10, 10, 10, 10);
            final EditText editText = new EditText(this);
            editText.setHint("Enter" + key);
            layout.addView(editText);
            builder.setView(layout);

            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String value = editText.getText().toString().trim();
                    if (!TextUtils.isEmpty(value)) {
                        progressBar.setVisibility(View.VISIBLE);


                        HashMap<String, Object> result = new HashMap<>();
                        result.put(key, value);

                        fbDB = FirebaseDatabase.getInstance();
                        dbRef = fbDB.getReference("Users");
                        dbRef.child(firebaseUser.getUid()).setValue(result)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.GONE);

                                        // after updated we will show updated
                                        Toast.makeText(EditNameActivity.this, " updated ",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //pd.dismiss();
                                        Toast.makeText(EditNameActivity.this, "Unable to update",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                        if (key.equals("name")) {
                            final DatabaseReference databaser = FirebaseDatabase.getInstance().getReference("Posts" +
                                    firebaseAuth.getCurrentUser().getUid()+"name");
                            databaser.child(uid).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditNameActivity.this, " updated ",Toast.LENGTH_SHORT).show();

                                }
                            });

                            Query query = databaser.orderByChild("uid").equalTo(uid);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        String child = databaser.getKey();
                                        dataSnapshot1.getRef().child("uname").setValue(value);
                                    }
                                }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        }

                    } else {
                        Toast.makeText(EditNameActivity.this, "Unable to update",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //pd.dismiss();
                }
            });
            builder.create().show();
        }



}

