package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlogUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BlogUsersAdapter blogUsersAdapter;
    List<BlogUsersModel> blogUsersModelList;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_users);

                recyclerView = findViewById(R.id.recyclerViewBlogUsers);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                blogUsersModelList = new ArrayList<>();
                firebaseAuth = FirebaseAuth.getInstance();
                getAllUsers();
                }

            private void getAllUsers() {
                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        blogUsersModelList.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            BlogUsersModel modelUsers = dataSnapshot1.getValue(BlogUsersModel.class);
                            if (modelUsers.getUid() != null && !modelUsers.getUid().equals(firebaseUser.getUid())) {
                                blogUsersModelList.add(modelUsers);
                            }
                            blogUsersAdapter = new BlogUsersAdapter(BlogUsersActivity.this, blogUsersModelList);
                            recyclerView.setAdapter(blogUsersAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            /*  @Override
          public void onCreate(@Nullable Bundle savedInstanceState) {
                setHasOptionsMenu(true);
                super.onCreate(savedInstanceState);
            }*/
        }


