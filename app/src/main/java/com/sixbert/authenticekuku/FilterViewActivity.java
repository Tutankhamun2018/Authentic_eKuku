package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterViewActivity extends AppCompatActivity {

    private Query query;

    private List<BuyItems> items = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BuyItemAdapter mAdapter;
    private ProgressBar loadPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        query = db.collection("eKuku");
        initControls();
    }

    private void initControls() {
        mRecyclerView = findViewById(R.id.filtered_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FilterAdapter(Preferences.filters, filterRecyclerView);
        mRecyclerView.setAdapter(mAdapter);


        loadPB = findViewById(R.id.loadPB);
        loadPB.setVisibility(View.VISIBLE);
        query.orderBy(Preferences.ORDER_BY).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                items = task.getResult().toObjects(Filter.class);

                if (!Preferences.filters.isEmpty()) {
                    ArrayList<BuyItems> filteredItems = new ArrayList<>();
                    List<String> type = Preferences.filters.get(Filter.INDEX_TYPE).getSelected();
                    List<String> qty = Preferences.filters.get(Filter.INDEX_QTY).getSelected();
                    List<String> prices = Preferences.filters.get(Filter.INDEX_PRICE).getSelected();
                    for (BuyItems item : items) {
                        boolean typeMatched = type.size() <= 0 || type.contains(item.getTypeOfItem());
                        boolean qtyMatched = true;
                        if (qty.size() > 0 && !qty.contains(item.getNumberOfProduct().toString())) {
                            qtyMatched = false;
                        }
                        boolean priceMatched = true;
                        if (prices.size() > 0 && !prices.contains(item.getPriceOfProduct())) {
                            priceMatched = false;
                        }
                        if (typeMatched && qtyMatched && priceMatched) {
                            filteredItems.add(item);
                        }
                    }
                    items = filteredItems;
                }

                mAdapter = new FilterValuesAdapter(filters, filterRecyclerView);
                mRecyclerView.setAdapter(mAdapter);
                loadPB.setVisibility(View.GONE);
            }
        });

    }
}