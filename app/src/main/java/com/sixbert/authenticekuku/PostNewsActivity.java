
package com.sixbert.authenticekuku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ProgressBar progressBar;
    List<PostModel> posts;
    FirebaseAuth firebaseAuth;
    String  postID, postUid;

    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
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
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_post_news);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);



        postID = getIntent().getStringExtra("pid");
        postUid = getIntent().getStringExtra("postUid");

        progressBar = findViewById(R.id.progress_bar_blog);

        fabNewPost = findViewById(R.id.fabNewPost);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerViewPosts);
        toolbar =findViewById(R.id.toolbar);

        if (toolbar!=null){
            setSupportActionBar(toolbar);
        }

        //checkConnectivity();

        progressBar.setVisibility(View.VISIBLE);

        int i = 0;
        setProgressValue(i);

        loadPosts();

        fabNewPost.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PostActivity.class)));


    }

    private void loadPosts() {



        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        posts = new ArrayList<>();

        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(uid);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");//;
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {

            //String results =
            //addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                posts.clear();
                if (dataSnapshot.exists()) {
                    //String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Exists");
                } else {
                    Log.d(TAG, "doesnt exist");
                }
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    //for (DataSnapshot dsnapshot : dataSnapshot1.getChildren()) {

                        PostModel postModel = dataSnapshot1.getValue(PostModel.class);
                        assert postModel != null;
                        Log.d(TAG, "post: " + postModel.getPost());

                        posts.add(postModel);

                    //}
                }
                    postAdapter = new PostAdapter(getApplicationContext(), posts);
                    recyclerView.setAdapter(postAdapter);

                    progressBar.setVisibility(View.GONE);


                
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


   private void setProgressValue(final int i) {

       // set the progress
       progressBar.setProgress(i);
       // thread is used to change the progress value
       Thread thread = new Thread(() -> {
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           setProgressValue(i + 10);
       });
       thread.start();
   }

}


