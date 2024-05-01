package com.sixbert.authenticekuku;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_menu);
        TextView editName = findViewById(R.id.editNameTv);
        TextView addPhoto = findViewById(R.id.addPhotoTv);
        TextView deleteAcct = findViewById(R.id.deleteAcctTv);

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditNameActivity.class);
                startActivity(intent);

            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);

            }
        });

        deleteAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
                //startActivity(new Intent (AccountMenu.this, AccountSettings.class));

            }
        });
    }

    private void deleteAccount() {

        /*new MaterialAlertDialogBuilder(this, R.style.AlertInterfaceDialogTheme).setTitle("Futa akaunti ")
                .setMessage("Akaunti yako itaondolewa katika mfumo wa eKuku. Una uhakika unataka kufuta?")
                .setPositiveButton("FUTA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        assert currentUser != null;
                        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AccountMenu.this, "Oops!.Tunasikitika unaondoka!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), VerifyPhoneActivity.class));//replace MainActivity with Subscriptions.Activity
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error", "Failed to remove user", e);
                            }
                        });

                    }


                })
                .setNegativeButton("GHAIRI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        finish();

                    }
                }).setCancelable(false)
                .create()
                .show();//.setCancelable(false).show();*/
        String url = "https://dasgtech.com/blog/ekuku-account-deletion/";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

    }
}

