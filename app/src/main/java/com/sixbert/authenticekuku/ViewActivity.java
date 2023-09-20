package com.sixbert.authenticekuku;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.Date;

public class ViewActivity extends AppCompatActivity {

        FirebaseFirestore db;
        private RecyclerView recyclerView;
    private SellItemsAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Window win = getWindow();
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            win.setStatusBarColor(Color.TRANSPARENT);
            setContentView(R.layout.activity_view);
            setTitle("eKuku");

            recyclerView = findViewById(R.id.recycler_item_view);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            Date morrow = new Date();
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(morrow);
            calendar.add(java.util.Calendar.DATE, 1);
            morrow = calendar.getTime();

            //yesterday
            Date yesterday = new Date();
            java.util.Calendar calendaryesterday = java.util.Calendar.getInstance();
            calendaryesterday.setTime(yesterday);
            calendaryesterday.add(Calendar.DATE, -1);
            yesterday =calendaryesterday.getTime();

            db = FirebaseFirestore.getInstance();
            Query query = db.collection("eKuku").whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
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