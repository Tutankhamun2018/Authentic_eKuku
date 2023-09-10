package com.sixbert.authenticekuku;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;


public class SellActivity extends AppCompatActivity {
    Context context;
    ImageView imageView;
    String TAG = "URL";
    SpinnerDatabaseHelper databaseHelper;
    HashMap<String, Object> map = new HashMap<>();

    long imageTime = System.currentTimeMillis();

    String townValue, wardValue;
    private AutoCompleteTextView autoTvDistrict, autoTvWard, autoTvStreet;
    private EditText numberOfProduct;
    private EditText priceOfProduct;

    private EditText extraDescription;
    NavigationBarView bottomNavigationItemView;
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    //public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private Uri imageUri = null;
    private final int i =0;

    TextView textView;
    AutoCompleteTextView autCompleteTV;

    StorageReference storageReference;

    ProgressBar progressBar;





    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;

    {
        assert currentUser != null;
        uid = currentUser.getPhoneNumber();
        //uid = currentUser.getDisplayName();
    }


    ArrayAdapter<String> adapter;



    public SellActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sell);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        progressBar = findViewById(R.id.progress_bar);
        imageView = findViewById(R.id.sellerImPreView);
        autoTvDistrict = findViewById(R.id.districtTextView);
        autoTvWard = findViewById(R.id.wardTextView);
        autoTvStreet = findViewById(R.id.streetTextView);
        //textView = findViewById(R.id.tvProgress);
        context = this;//Advert View
        numberOfProduct = findViewById(R.id.edtxtnumber_of_chicken);
        priceOfProduct = findViewById(R.id.edtxtPrice);
        extraDescription = findViewById(R.id.xtraDescription);
        Button add = findViewById(R.id.btnUpdate);
        Button edit = findViewById(R.id.btnEdit);

        Button selectImage = findViewById(R.id.selectImage);

        ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            assert result.getData() != null;
                            imageUri = result.getData().getData();
                            imageView.setImageURI(imageUri);
                        }

                    }


                });
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(intent);
            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        databaseHelper = new SpinnerDatabaseHelper(this, "Sellerlocation_v01.db", null, 1);

        try {
            databaseHelper.checkDB();

            fillSpinner(context, autoTvDistrict, "Towns", "District", "");

            autoTvDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    townValue = parent.getItemAtPosition(position).toString();

                    fillSpinner(context, autoTvWard, "Towns", "Ward", "where District = '" + townValue + "'");


                }


            });

            autoTvWard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    wardValue = parent.getItemAtPosition(position).toString();
                    fillSpinner(context, autoTvStreet, "Towns", "Street", "where District ='" + townValue + "'and Ward ='" + wardValue + "'");

                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        bottomNavigationItemView = findViewById(R.id.bottom_navigation);

        bottomNavigationItemView.setSelectedItemId(R.id.sell_activity);//continue

        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem itemBtm) {
                int itemIdBtm = itemBtm.getItemId();
                if (itemIdBtm == R.id.sell_activity) {
                    return true;
                } else if (itemIdBtm == R.id.edu_activity) {
                    startActivity(new Intent(getApplicationContext(), EduActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemIdBtm == R.id.buy_activity) {
                    startActivity(new Intent(getApplicationContext(), BuyActivity2.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemIdBtm == R.id.home1) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                }
                return false;


            }
        });

        String[] product = {getResources().getString(R.string.localchicken), getResources().getString(R.string.broilerchicken),
                getResources().getString(R.string.hybridchicken), getResources().getString(R.string.egglocal_s),
                getResources().getString(R.string.egglayers_s), getResources().getString(R.string.egghybrid_s)};


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, product);
        TextInputLayout textInputLayout = findViewById(R.id.customerSpinnerLayout);
        autCompleteTV = findViewById(R.id.productTextView);

        autCompleteTV.setAdapter(adapter);

        autCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SellActivity.this, autCompleteTV.getText() + " selected", Toast.LENGTH_SHORT).show();
                String item = autCompleteTV.getText().toString();

                if (item.equals("Chagua Bidhaa")) {
                    textInputLayout.setError("Chagua Bidhaa");
                    textInputLayout.requestFocus();
                }
            }
        });


        priceOfProduct.addTextChangedListener(onTextChangedListener());




        add.setOnClickListener(view -> {

            String txt_district = autoTvDistrict.getText().toString();
            String txt_ward = autoTvWard.getText().toString();
            String txt_street = autoTvStreet.getText().toString();
            String txt_autocompleteTV = autCompleteTV.getText() + "";
            String txt_numberOfChicken = numberOfProduct.getText() + "";
            String txt_priceOfChicken = priceOfProduct.getText() + "";
            if (txt_district.trim().isEmpty() || txt_ward.trim().isEmpty() || txt_street.trim().isEmpty() ||
                    txt_autocompleteTV.trim().isEmpty() || txt_numberOfChicken.trim().isEmpty() ||
                    txt_priceOfChicken.trim().isEmpty()) {
                Toast.makeText(SellActivity.this, "Jaza kikamilifu tafadhali", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                setProgressValue(i);


                addDataToFirestore();
            }
        });





        edit.setOnClickListener(view -> startActivity(new Intent(SellActivity.this, ViewActivity.class)));

    }



    private void addDataToFirestore(){
        if (imageUri != null) {
            Bitmap bmp;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] data = baos.toByteArray();

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            StorageReference sellRef = FirebaseStorage.getInstance().getReference().child("Sales/"
                    + firebaseAuth.getCurrentUser().getUid()+"/"+ imageTime);


            sellRef.putBytes(data).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;

                            String downloadUrl = uriTask.getResult().toString();
                            if (uriTask.isSuccessful()) {


                                Calendar calendar = Calendar.getInstance();
                                Date currentDate = calendar.getTime();
                                Timestamp today = new Timestamp(currentDate);


                                //HashMap<String, Object> map = new HashMap<>();
                                map.put("phoneNumber", uid);
                                map.put("today", today); //Date timestamp
                                map.put("townOfSeller", autoTvDistrict.getText().toString());
                                map.put("wardOfSeller", autoTvWard.getText().toString());
                                map.put("streetOfSeller", autoTvStreet.getText().toString());
                                map.put("typeOfItem", autCompleteTV.getText().toString());
                                map.put("imageUrl", downloadUrl);
                                //map.put("phone number", phoneNumber.getText().toString());
                                map.put("numberOfProduct", numberOfProduct.getText().toString());
                                map.put("priceOfProduct", priceOfProduct.getText().toString().replaceAll(",", ""));//remove thousand comma separator
                                //FirebaseDatabase.getInstance().getReference().child("eKuku").child(autCompleteTV.getText()+"").updateChildren(map);
                                map.put("extraDescription", extraDescription.getText().toString());


                                db.collection("eKuku")
                                        .add(map)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                autoTvDistrict.setText("");
                                                autoTvWard.setText("");
                                                autoTvStreet.setText("");
                                                autCompleteTV.setText("");
                                                numberOfProduct.setText("");
                                                priceOfProduct.setText("");
                                                extraDescription.setText("");
                                                imageView.setVisibility(View.GONE);
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(SellActivity.this, "Bidhaa zako zimeongezwa kikamilifu",
                                                        Toast.LENGTH_SHORT).show();


                                            }
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(SellActivity.this,
                                                "Bidhaa hazijaongezwa", Toast.LENGTH_SHORT).show());

                                //imageView.setVisibility(View.GONE);

                            }
                        }
                    });
        }

        }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence edtNumberString, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence edtNumberString, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable edtNumberString) {
                priceOfProduct.removeTextChangedListener(this);
                try {
                    String originalString = edtNumberString.toString();
                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);
                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###");
                    String formattedString = formatter.format(longval);
                    //setting text after format to EditText
                    priceOfProduct.setText(formattedString);
                    priceOfProduct.setSelection(priceOfProduct.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                priceOfProduct.addTextChangedListener(this);
            }
        };
    }

    //DBase continues here
    @SuppressLint("Range")
    private void fillSpinner(Context context, AutoCompleteTextView autoTV, String table,
                             String column, String where) {
        SQLiteDatabase db = databaseHelper.openDatabase("Sellerlocation_v01.db");

        ArrayList<String> mArray = new ArrayList<>();

        Cursor cursor = db.rawQuery("Select distinct " + column + " from " + table + "  " + where,
                null);

        while (cursor.moveToNext()) {
            mArray.add(cursor.getString(cursor.getColumnIndex(column)));
        }
        cursor.close();
        db.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_selectable_list_item, mArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoTV.setAdapter(adapter);
    }

    private void setProgressValue(final int i) {

        // set the progress
        progressBar.setProgress(i);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(i + 10);
            }
        });
        thread.start();
    }



    }






