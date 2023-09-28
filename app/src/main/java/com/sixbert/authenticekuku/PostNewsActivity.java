
package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class PostNewsActivity extends AppCompatActivity {



    static final String TAG ="kumekucha";
    RecyclerView recyclerView;
    FloatingActionButton fabNewPost;

    PostAdapter postAdapter;

    Toolbar toolbar;
    List<PostModel> posts;
    FirebaseAuth firebaseAuth;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;

    {
        assert currentUser != null;
        uid = currentUser.getUid();
        uid = currentUser.getDisplayName();
    }

    //private FirestoreRecyclerAdapter<InAppChat, ChatAdapter.ViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_post_news);

        fabNewPost = findViewById(R.id.fabNewPost);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerViewPosts);
        toolbar =findViewById(R.id.toolbar);

        if (toolbar!=null){
            setSupportActionBar(toolbar);
        }

        //checkConnectivity();

        loadPosts();

        fabNewPost.setOnClickListener(v -> {
            Intent intent = new Intent(PostNewsActivity.this, PostActivity.class);
            startActivity(intent);
        });


    }

    private void loadPosts() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        posts = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                if(dataSnapshot.exists()){
                //String value = dataSnapshot.getValue(String.class);
               Log.d(TAG, "Exists");
                }else{
                    Log.d(TAG, "doesnt exist");
                }
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    PostModel postModel = dataSnapshot1.getValue(PostModel.class);
                    assert postModel != null;
                   Log.d(TAG, "post: " + postModel.getPost());

                    posts.add(postModel);
                }
                    postAdapter = new PostAdapter(getApplicationContext(), posts);
                    recyclerView.setAdapter(postAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_profile_menu, menu);
        return true;

}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id ==R.id.edit_profile){


            Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
            startActivity(intent);
            //overridePendingTransition(0,0);
            return true;

        }

        if (id ==R.id.edit_name){


            Intent intent = new Intent(getApplicationContext(), EditNameActivity.class);
            startActivity(intent);
            //overridePendingTransition(0,0);
            return true;

        }

        if (id == R.id.blog_rules){
            Intent intentBlogRules = new Intent(getApplicationContext(), BlogRulesActivity.class);
            startActivity(intentBlogRules);
        }


        return super.onOptionsItemSelected(item);
}


   /* private void checkConnectivity() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        registerReceiver(new ConnectionReceiver(), intentFilter);

        ConnectionReceiver.Listener = this::onNetworkChange;

        ConnectivityManager cmanager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cmanager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            Toast.makeText(PostNewsActivity.this, "OK!", Toast.LENGTH_SHORT).show();

        } else {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oops!");
            builder.setCancelable(true);
            builder.setMessage("Pole!.. Hujaunganishwa kwenye Intanet, angalia mtandao na ujaribu tena");
            builder.setNegativeButton("Funga", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        }catch (Exception e)

        {
            e.printStackTrace();
        }

    }
    public void onNetworkChange(boolean isConnected){
        showAlertDialog();
    }*/



}


