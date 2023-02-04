package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initControls();
    }

    private void initControls() {
        RecyclerView filterRecyclerView = findViewById(R.id.filterRV);
        RecyclerView filterValuesRecyclerView = findViewById(R.id.filterValuesRV);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterValuesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> typeOfItems = Arrays.asList("Kuku Kienyeji", "Kuku Kisasa", "Mayai Kisasa (Trei)",
                "Mayai Kienyeji (Trei)", "Kuku Chotara", "Mayai Chotara (Trei)");
        if (!Preferences.filters.containsKey(Filter.INDEX_TYPE)) {
            Preferences.filters.put(Filter.INDEX_TYPE, new Filter("Bidhaa", typeOfItems, new ArrayList<>()));
        }

        List<String> qtyOfProduct = Arrays.asList("1-50", "51-100", "101-150", "151-200", "201-300", ">301");
        if (!Preferences.filters.containsKey(Filter.INDEX_QTY)) {
            Preferences.filters.put(Filter.INDEX_QTY, new Filter("Kiasi", qtyOfProduct, new ArrayList<>()));
        }
        List<String> priceOfProduct = Arrays.asList("5000-7999", "8000-10999", "11000-13999", "14000-16999", "17000-19999", ">20000");
        if (!Preferences.filters.containsKey(Filter.INDEX_PRICE)) {
            Preferences.filters.put(Filter.INDEX_PRICE, new Filter("Bei", priceOfProduct, new ArrayList<>()));
        }

        FilterAdapter filterAdapter = new FilterAdapter(Preferences.filters, filterRecyclerView);
        filterRecyclerView.setAdapter(filterAdapter);

        FilterAdapter filterAdapterV = new FilterAdapter(Preferences.filters, filterValuesRecyclerView);
        filterRecyclerView.setAdapter(filterAdapterV);

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.filters.get(Filter.INDEX_TYPE).setSelected(new ArrayList<>());
                Preferences.filters.get(Filter.INDEX_QTY).setSelected(new ArrayList<>());
                Preferences.filters.get(Filter.INDEX_PRICE).setSelected(new ArrayList<>());
                startActivity(new Intent(FilterActivity.this, FilterViewActivity.class));
                finish();
            }
        });
        Button btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FilterActivity.this, FilterViewActivity.class));
                finish();
            }
        });

    }
}