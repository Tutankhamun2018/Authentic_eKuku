package com.sixbert.authenticekuku;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class EditKukuActivity extends AppCompatActivity {
    FirebaseFirestore db;
    //Spinner spinnerTown, spinnerStreet;
    private EditText extraDescription;
    private AutoCompleteTextView autoTvDistrict, autoTvWard, autoTvStreet;
    Context context;
    long imageTime = System.currentTimeMillis();
    SpinnerDatabaseHelper databaseHelper;
    HashMap<String, Object> map = new HashMap<>();

    String townValue, wardValue;
    EditText numberOfProduct;
    EditText priceOfProduct;
    String documentId;
    private Uri imageUri = null;
    ImageView imageView;
    ProgressBar progressBar;
    Button selectImage, btnUpdate;
    AutoCompleteTextView autCompleteTV;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;

    String UUD;

    {
        assert currentUser != null;
        uid = currentUser.getPhoneNumber();
        UUD = currentUser.getUid();
    }

    ArrayAdapter<String> adapter;


    String[] product = {"Kuku Kienyeji", "Kuku Kisasa", "Mayai Kisasa (Trei)", "Mayai Kienyeji (Trei)",
            "Kuku Chotara", "Mayai Chotara (Trei)"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_edit_kuku);

        numberOfProduct = findViewById(R.id.edtxtnumber_of_chicken);
        priceOfProduct = findViewById(R.id.edtxtPrice);
        extraDescription = findViewById(R.id.xtraDescription);
        autCompleteTV = findViewById(R.id.productTextView);
        progressBar = findViewById(R.id.editPB);
        btnUpdate = findViewById(R.id.btnUpdate);
        imageView = findViewById(R.id.editImageView);
        selectImage = findViewById(R.id.editImage);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        documentId = intent.getStringExtra("documentId");
        if (documentId == null || documentId.isEmpty()) {
            startActivity(new Intent(this, ViewActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }

        initControls();


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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_district = autoTvDistrict.getText().toString();
                String txt_ward = autoTvWard.getText().toString();
                String txt_street = autoTvStreet.getText().toString();
                String txt_autocompleteTV = autCompleteTV.getText().toString();
                String txt_numberOfChicken = numberOfProduct.getText().toString();
                String txt_priceOfChicken = priceOfProduct.getText().toString();
                if (txt_district.trim().isEmpty() || txt_ward.trim().isEmpty() || txt_street.trim().isEmpty() ||
                        txt_autocompleteTV.trim().isEmpty() || txt_numberOfChicken.trim().isEmpty() ||
                        txt_priceOfChicken.trim().isEmpty()) {
                    Toast.makeText(EditKukuActivity.this, "Jaza kikamilifu tafadhali", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    //setProgressValue(i);


                    addDataToFirestore();
                }
            }
        });

        FirebaseFirestore dbFire = FirebaseFirestore.getInstance();
        DocumentReference docRef = dbFire.collection("eKuku").document(UUD)
                .collection("postId").document(documentId);//.document(documentId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    BuyItems item = documentSnapshot.toObject(BuyItems.class);

                    assert item != null;
                    autoTvDistrict.setText(String.valueOf(item.getTownOfSeller()));
                    autoTvWard.setText(String.valueOf(item.getWardOfSeller()));
                    autoTvStreet.setText(String.valueOf(item.getStreetOfSeller()));
                    autCompleteTV.setText(String.valueOf(item.getTypeOfItem()));
                    numberOfProduct.setText(String.valueOf(item.getNumberOfProduct()));
                    priceOfProduct.setText(String.valueOf(item.getPriceOfProduct()));
                    extraDescription.setText(String.valueOf(item.getExtraDescription()));

                }
            }
        });
    }

    private void initControls() {
        autoTvDistrict = findViewById(R.id.districtTextView);
        autoTvWard = findViewById(R.id.wardTextView);
        autoTvStreet = findViewById(R.id.streetTextView);
        context = this;

        databaseHelper = new SpinnerDatabaseHelper(this, "Sellerlocation_v01.db", null, 1);

        try {
            databaseHelper.checkDB();

            fillSpinner(context, autoTvDistrict, "District", "");

            autoTvDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    townValue = parent.getItemAtPosition(position).toString();

                    fillSpinner(context, autoTvWard, "Ward", "where District = '" + townValue + "'");


                }

            });

            autoTvWard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    wardValue = parent.getItemAtPosition(position).toString();
                    fillSpinner(context, autoTvStreet, "Street", "where District ='" + townValue + "'and Ward ='" + wardValue + "'");

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, product);
        TextInputLayout textInputLayout = findViewById(R.id.customerSpinnerLayout);
        AutoCompleteTextView autCompleteTV = findViewById(R.id.productTextView);

        autCompleteTV.setAdapter(adapter);
        autCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(EditKukuActivity.this, autCompleteTV.getText() + " selected", Toast.LENGTH_SHORT).show();
                String item = autCompleteTV.getText().toString();

                if (item.isEmpty()) {
                    textInputLayout.setError("Chagua Bidhaa");
                    textInputLayout.requestFocus();
                }
            }
        });
    }


    private void addDataToFirestore() {
        if (imageUri != null) {
            Bitmap bmp;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Bitmap bmp = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] data = baos.toByteArray();

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            StorageReference sellRef = FirebaseStorage.getInstance().getReference().child("Sales/"
                    + firebaseAuth.getCurrentUser().getUid() + "/" + imageTime);


            sellRef.putBytes(data).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;

                            String downloadUrl = uriTask.getResult().toString();
                            if (uriTask.isSuccessful()) {

                                //final String postId = String.valueOf(System.currentTimeMillis());

                                Calendar calendar = Calendar.getInstance();
                                Date currentDate = calendar.getTime();
                                Timestamp today = new Timestamp(currentDate);


                                //HashMap<String, Object> map = new HashMap<>();
                                map.put("phoneNumber", uid);
                                map.put("today", today); //timestampdateformat
                                map.put("townOfSeller", autoTvDistrict.getText().toString());
                                map.put("wardOfSeller", autoTvWard.getText().toString());
                                map.put("streetOfSeller", autoTvStreet.getText().toString());
                                map.put("typeOfItem", autCompleteTV.getText().toString());
                                map.put("imageUrl", downloadUrl);
                                map.put("uid", UUD);
                                map.put("numberOfProduct", numberOfProduct.getText().toString());
                                map.put("priceOfProduct", priceOfProduct.getText().toString()
                                        .replaceAll(",", ""));//remove thousand comma separator
                                map.put("extraDescription", extraDescription.getText().toString());

                              final DocumentReference docRef =  db.collection("eKuku")
                                        .document(UUD).collection("postId").document(documentId);
                              docRef.update(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                autoTvDistrict.setText("");
                                                autoTvWard.setText("");
                                                autoTvStreet.setText("");
                                                autCompleteTV.setText("");
                                                numberOfProduct.setText("");
                                                priceOfProduct.setText("");
                                                extraDescription.setText("");
                                                imageView.setVisibility(View.GONE);
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(EditKukuActivity.this, "Bandiko limehaririwa",
                                                        Toast.LENGTH_SHORT).show();


                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(EditKukuActivity.this, "Bandiko halijahaririwa", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        });
                            }
                        }

                    });
        }
    }




    //DBase continues here
    @SuppressLint("Range")
    private void fillSpinner (Context context, AutoCompleteTextView autoTV,
                              String column, String where){
        SQLiteDatabase db = databaseHelper.openDatabase("Sellerlocation_v01.db");

        ArrayList<String> mArray = new ArrayList<>();

        Cursor cursor = db.rawQuery("Select distinct "+column+" from "+ "Towns" +" "+where,
                null);

        while (cursor.moveToNext()){
            mArray.add(cursor.getString(cursor.getColumnIndex(column)));
        }
        cursor.close();
        db.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_selectable_list_item, mArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoTV.setAdapter(adapter);
    }

}
