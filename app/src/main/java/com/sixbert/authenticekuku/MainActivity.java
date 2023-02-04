package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //private Button logout;
    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*public void checkCurrentUser() {
            // [START check_current_user]
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in
            } else {
                // No user is signed in
            }
            // [END check_current_user]
        }*/

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //logout = findViewById(R.id.btnLogout);

        //Attach the ViewPagerAdapter to the ViewPager

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(mViewPagerAdapter);

        //Attach the View pager to the TabLayout
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Uza");
                        break;
                    case 1:
                        tab.setText("Nunua");
                        break;
                    case 2:
                        tab.setText("Zaidi");
                        break;
                }
            }

            // public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


        }).attach();

        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();

                //after loging out, the user shoulg go back to the start activity

                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });*/

    }
}


/*

//starthere
    String[]  product = {"Kuku Kienyeji", "Kuku Kisasa","Mayai Kisasa", "Mayai Kienyeji",
                "Kuku Chotara", "Mayai Chotara"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, product);

        AutoCompleteTextView autCompleteTV = findViewById(R.id.productTextView);

        autCompleteTV.setAdapter(adapter);

        //Use OnItemClickLister instead of onItemSelectedListener
        //onItemSelectedListener works if you use toggle arrows or trackball

        autCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, autCompleteTV.getText() +" selected", Toast.LENGTH_SHORT).show();

            }
        });


        //Set adapter

        phoneNumber = findViewById(R.id.edtxtphone);
        numberOfProduct = findViewById(R.id.edtxtnumber_of_chicken);
        priceOfProduct = findViewById(R.id.edtxtPrice);
        //productAutoTV =findViewById(R.id.productTextView);
        add = findViewById(R.id.btnAdd);

        logout = findViewById(R.id.btnLogout);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_phoneNumber = phoneNumber.getText() +"";

                /*String txt_numberOfItem =  String.format("s%:%s", "Number of", autCompleteTV.getText().toString());
                if(autCompleteTV.getText() != null) {
                    txt_numberOfItem = autCompleteTV.getOnItemClickListener()+"";
                        switch (autCompleteTV){
                            case 0:
                             = txt_numberOfItem;
                            break;


                 //   }
               // }
                //if (txt_numberOfItem() !null) swich
                //String txt_priceOfItem =  String.format("s%:%s", "Price of", autCompleteTV.getText().toString())

                String txt_numberOfChicken = numberOfProduct.getText()+"";
                String txt_priceOfChicken = priceOfProduct.getText() +"";
                if (TextUtils.isEmpty(txt_phoneNumber) && TextUtils.isEmpty(txt_numberOfChicken) && TextUtils.isEmpty(txt_priceOfChicken)){
                    Toast.makeText(MainActivity.this, "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                } else{

                    HashMap< String, Object> map = new HashMap<>();
                    map.put("Number of Product", numberOfProduct.getText().toString());
                    map.put("Price of product", priceOfProduct.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("eKuku").child(autCompleteTV.getText()+"").updateChildren(map);
                    //FirebaseDatabase.getInstance().getReference().child("eKuku").push().child("Phone Number").setValue(txt_phoneNumber);
                }
            }
        });*/
    /*    logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();

                //after loging out, the user shoulg go back to the start activity

                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        //set a firebase instance database
        //add a main branch (child1), sub-branch (child2)
        //FirebaseDatabase.getInstance().getReference().child("eKuku").child("Kuku").setValue("uza");
        //if we want a branch to have more than one child, we use a hashmap

       // HashMap< String, Object> map = new HashMap<>();
       // map.put("Name", text);
        //map.put("Email", "sixbert.mourice@gmail.com");
       // FirebaseDatabase.getInstance().getReference().child("eKuku").child("Multivalues").updateChildren(map);
        //creation of database is possible if the user is logged in

        //create UI which can take user inputs to the database
        //use the activity_main.xml
       /* initUI();

    }



    }


}*/

