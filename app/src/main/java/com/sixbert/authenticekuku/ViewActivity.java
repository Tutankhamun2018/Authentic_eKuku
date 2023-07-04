package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewActivity extends AppCompatActivity {

        FirebaseFirestore db;
        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private SellItemsAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view);
            setTitle("eKuku");

            recyclerView = findViewById(R.id.recycler_item_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            db = FirebaseFirestore.getInstance();
            Query query = db.collection("eKuku").orderBy("today", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<BuyItems> options = new FirestoreRecyclerOptions.Builder<BuyItems>()
                    .setQuery(query, BuyItems.class)
                    .build();

            adapter = new SellItemsAdapter(this, options);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected void onStart() {
            super.onStart();
            adapter.startListening();
            recyclerView.getRecycledViewPool().clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (adapter != null) {
                adapter.stopListening();
            }
        }
    }