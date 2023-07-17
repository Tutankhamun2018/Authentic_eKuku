package com.sixbert.authenticekuku;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    StorageReference storageReference;

    ProgressBar progressBar;





    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;

    {
        assert currentUser != null;
        uid = currentUser.getPhoneNumber();
    }


    ArrayAdapter<String> adapter;


    //district = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Wilaya)));
    //final String[] morogoro = getResources().getStringArray(R.array.kata_moro_mjini);
    //final String[] kibaha = getResources().getStringArray(R.array.kata_kibaha_mjini);

    public SellActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Advert View
        //MobileAds.initialize(this);//the SDK can reference the appID declared in the AndroidManifest
        //AdView adView = (AdView) findViewById(R.id.banner_adSell);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);

        drawerLayout = findViewById(R.id.drawer_layout);
        //navigationView = findViewById(R.id.nav_view);
        progressBar = findViewById(R.id.progressBar);
        //progressBar =new ProgressBar(SellActivity.this, null, android.R.attr.progressBarStyleLarge);
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        //params.addRule(RelativeLayout.CENTER_IN_PARENT);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

       /* navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                drawerLayout.closeDrawers();

                if(itemId ==R.id.nav_sell){
                    return true;
                } else if (itemId ==R.id.nav_home){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId ==R.id.nav_buy){
                    startActivity(new Intent(getApplicationContext(), BuyActivity2.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId ==R.id.nav_edu){
                    startActivity(new Intent(getApplicationContext(), EduActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }

                return false;
            }
        });*/


        autoTvDistrict = findViewById(R.id.districtTextView);
        autoTvWard = findViewById(R.id.wardTextView);
        autoTvStreet = findViewById(R.id.streetTextView);
        context = this;

        databaseHelper = new SpinnerDatabaseHelper(this, "Sellerlocation_2.db", null, 1);

        try {
            databaseHelper.checkDB();

            fillSpinner(context, autoTvDistrict, "Towns", "Town", "");

            autoTvDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    townValue = parent.getItemAtPosition(position).toString();

                    fillSpinner(context, autoTvWard, "Towns", "Street", "where Town = '" + townValue + "'");


                }


            });

            autoTvWard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    wardValue = parent.getItemAtPosition(position).toString();
                    fillSpinner(context, autoTvStreet, "Towns", "Substreet", "where Town ='" + townValue + "'and Street ='" + wardValue + "'");

                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationItemView.setSelectedItemId(R.id.sell_activity);//continue
        // implement item selected listener
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


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, product);
        TextInputLayout textInputLayout = findViewById(R.id.customerSpinnerLayout);
        AutoCompleteTextView autCompleteTV = findViewById(R.id.productTextView);

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

        //phoneNumber = rootView.findViewById(R.id.edtxtphone);
        numberOfProduct = findViewById(R.id.edtxtnumber_of_chicken);
        priceOfProduct = findViewById(R.id.edtxtPrice);
        extraDescription = findViewById(R.id.xtraDescription);

        priceOfProduct.addTextChangedListener(onTextChangedListener());
        //productAutoTV =findViewById(R.id.productTextView);
        //private TextView productAutoTV;
        Button add = findViewById(R.id.btnUpdate);
        Button edit = findViewById(R.id.btnEdit);

        Button selectImage = findViewById(R.id.selectImage);
        Button uploadImage = findViewById(R.id.uploadImage);
        imageView = findViewById(R.id.itemImage);


        //logout = rootView.findViewById(R.id.btnLogout);


        add.setOnClickListener(view -> {

            String txt_district = autoTvDistrict.getText().toString();
            String txt_ward = autoTvWard.getText().toString();
            String txt_street = autoTvStreet.getText().toString();
            //String imageUrl = getGeneratedUrl(storageReference);
            String txt_autocompleteTV = autCompleteTV.getText() + "";
            String txt_numberOfChicken = numberOfProduct.getText() + "";
            String txt_priceOfChicken = priceOfProduct.getText() + "";
            if (txt_district.trim().isEmpty()|| txt_ward.trim().isEmpty()||txt_street.trim().isEmpty()||
                    txt_autocompleteTV.trim().isEmpty()||txt_numberOfChicken.trim().isEmpty()||
                    txt_priceOfChicken.trim().isEmpty()) {
                Toast.makeText(SellActivity.this, "Jaza kikamilifu tafadhali", Toast.LENGTH_SHORT).show();
            } else {

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
                                Toast.makeText(SellActivity.this, "Bidhaa zako zimeongezwa kikamilifu", Toast.LENGTH_SHORT).show();


                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(SellActivity.this, "Bidhaa hazijaongezwa", Toast.LENGTH_SHORT).show());
            }
            imageView.setVisibility(View.GONE);
        });

        edit.setOnClickListener(view -> startActivity(new Intent(SellActivity.this, ViewActivity.class)));

        storageReference = FirebaseStorage.getInstance().getReference();

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

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UploadImage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });}

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
        SQLiteDatabase db = databaseHelper.openDatabase("Sellerlocation_2.db");

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


    private void UploadImage() throws IOException {
        if (imageUri != null) {
            Bitmap bmp =null;//AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //builder.setView(R.id.progressBar);
            //progressBar.setVisibility(View.VISIBLE);


            StorageReference ref = storageReference.child(uid);
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            //Rerotate back to its original orientation because the image rotates after compression
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap imageAfterRotation = Bitmap.createBitmap(bmp,0,0, bmp.getWidth(), bmp.getHeight(), matrix,true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageAfterRotation.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] data = baos.toByteArray();

            ref.putBytes(data).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                                    getGeneratedUrl(ref);//imageView.setImageURI(Uri.parse(""));

                                    progressBar.setVisibility(View.GONE);


                                    Toast.makeText(SellActivity.this, "Picha imepandishwa kikamilifu", Toast.LENGTH_SHORT).show();

                                }


                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SellActivity.this, "Picha haijapandishwa" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0*snapshot.getBytesTransferred()/
                                    snapshot.getTotalByteCount());

                            //progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(SellActivity.this, "Inapakia "+(int)progress +"%",Toast.LENGTH_LONG).show();

                        }
                    });

        }
    }


        private void getGeneratedUrl (final StorageReference reference){
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String downloadUrl = uri.toString();

                    Log.i(TAG, "url is "+downloadUrl);

                  // HashMap<String, Object> map = new HashMap<>();
                  map.put("imageUrl", downloadUrl);


                }
            });
        }
    }






