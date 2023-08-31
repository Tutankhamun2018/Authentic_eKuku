



package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class PostNewsActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore dbase;
    TabLayout tabLayout;
    Query query;
    static final String TAG ="kumekucha";
    RecyclerView recyclerView;
    FloatingActionButton fabNewPost;
    List<ChatModel> commentList;
    PostAdapter postAdapter;
    ImageView imageView;
    Toolbar toolbar;
    List<PostModel> posts;
    DatabaseReference dbRef;

    FirebaseAuth firebaseAuth;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;

    {
        assert currentUser != null;
        uid = currentUser.getUid();
        uid = currentUser.getDisplayName();
    }

    private FirestoreRecyclerAdapter<InAppChat, ChatAdapter.ViewHolder> adapter;
    private MultiAutoCompleteTextView input;
    private ProgressBar progressBar;
    Button send;
    private String userID;
    private String userName;
    Uri filepath;
    ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_post_news);


        //setHasOptionsMenu(true); for fragments only


        fabNewPost = findViewById(R.id.fabNewPost);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerViewPosts);
        toolbar =findViewById(R.id.toolbar);

        if (toolbar!=null){
            setSupportActionBar(toolbar);
        }

        loadPosts();
        //loadPostInfo();
        //loadUserInfo();
        //setLikes();
        //loadComments();

        fabNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostNewsActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });


    }



    private void loadPosts() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
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
                    //Log.d(TAG, dataSnapshot1.getValue(String.class));
                    //Log.d(TAG, dataSnapshot1.getValue());
                    /*if(dataSnapshot1.hasChild("uId")){
                        uid = dataSnapshot1.child("uId").getValue(String.class);
                        int index = Integer.parseInt(uid)+1;
                        uid = Integer.toString(index);
                        Log.d(TAG, "value is:" +uid);
                    }else {
                        uid ="1";
                        Log.d(TAG, "value " + uid);
                    }*/
                    posts.add(postModel);
                }
                    postAdapter = new PostAdapter(getApplicationContext(), posts);
                    recyclerView.setAdapter(postAdapter);


                    //Log.d("datasnapshot", dataSnapshot.getValue(String.class));

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

            //Toast.makeText(this, "You pressed update profile options", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
            startActivity(intent);
            //overridePendingTransition(0,0);
            return true;

        }

        if (id ==R.id.edit_name){

            //Toast.makeText(this, "You pressed update profile options", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), EditNameActivity.class);
            startActivity(intent);
            //overridePendingTransition(0,0);
            return true;

        }

        if (id == R.id.blog_rules){
            Intent intentBlogRules = new Intent(getApplicationContext(), BlogRulesPop.class);
            startActivity(intentBlogRules);
        }


        return super.onOptionsItemSelected(item);
}


}

 /*       recyclerView = findViewById(R.id.rVChatRoom);
        progressBar = findViewById(R.id.progressBar);
        send = findViewById(R.id.sendChat);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        dbase = FirebaseFirestore.getInstance();

        query = dbase.collection("eKukuChat").orderBy("timeStamp");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        adapter = new ChatAdapter(query, userID, PostNewsActivity.this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getID() == R.id.sendChat) {
            String messageText = input.getText().toString();
            if (TextUtils.isEmpty(messageText)) {
                Toast.makeText(PostNewsActivity.this, "no message", Toast.LENGTH_SHORT).show();
                return;
            }
            dbase.collection("eKukuChat").add(new InAppChat(userName, messageText, userID, 0, null));
            input.setText("");
        }
    }
}*/
