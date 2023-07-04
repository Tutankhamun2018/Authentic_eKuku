/*package com.sixbert.authenticekuku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class PostNewsActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore dbase;
    Query query;
    RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<InAppChat, ChatAdapter.MessageHolder> adapter;
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
        setContentView(R.layout.activity_post_news);

        recyclerView = findViewById(R.id.rVChatRoom);
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
