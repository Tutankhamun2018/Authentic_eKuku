package com.sixbert.authenticekuku;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpinnerActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI()
    {
        //UI reference of textView
        final AutoCompleteTextView productAutoTV = findViewById(R.id.productTextView);

        // create list of customer
        ArrayList<String> productList = getProductList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SpinnerActivity.this, android.R.layout.simple_spinner_item, productList);

        //Set adapter
        productAutoTV.setAdapter(adapter);

        //submit button click event registration
        /*/findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(MainActivity.this, productAutoTV.getText(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private ArrayList<String> getProductList()
    {
        ArrayList<String> product = new ArrayList<>();
        product.add("Kuku Kienyeji");
        product.add("Kuku Kisasa");
        product.add("Mayai Kisasa");
        product.add("Mayai Kienyeji");
        product.add("Kuku Chotara");
        product.add("Mayai Chotara");

        return product;
    }
}
