package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GraphicsWeeklyActivity extends AppCompatActivity {



        //GraphView graphView;
        //Context context;
        //String townValue;
        private int avgPriceDayOne;
        private int avgPriceDayTwo;
        private int avgPriceDayThree;
        private int avgPriceDayFour;
        private int avgPriceDayFive;
        private int avgPriceDaySix;
        private int avgPriceDaySeven;

        private Date dayZero;
        private Date dayOne;
        private Date dayTwo;
        private Date dayThree;
        private Date dayFour;
        private Date dayFive;
        private Date daySix;
        private Date daySeven;

        private Date dayEight;


        String stringDate;
        ArrayList<Entry> date_PriceList = new ArrayList<Entry>();
        private LineChart chart;
        //Date date;
        private  final  int fillColor = Color.argb(255, 255, 255, 255);

        SpinnerDatabaseHelper databaseHelper;
        String nameOfTown;
        private AutoCompleteTextView autoTvDistrict;

        public Toolbar toolbar;

        private final int i = 0;
        Button viewGraph;
        BuyItems buyItems;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault(Locale.Category.FORMAT));


        //CollectionReference collectionReference = db.collectionGroup("eKuku");
        String uid;
        String UUD;

        {
            assert currentUser != null;
            uid = currentUser.getPhoneNumber();
            UUD = currentUser.getUid();
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Window win = getWindow();
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            win.setStatusBarColor(Color.TRANSPARENT);
            overridePendingTransition(0, 0);
            setContentView(R.layout.activity_graphics_weekly);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //progressBar = findViewById(R.id.progress_bar);
            autoTvDistrict = findViewById(R.id.districtTextView);
            viewGraph = findViewById(R.id.btnViewGraphWeekly);

            chart = findViewById(R.id.lineChartWeekly);
            chart.setBackgroundColor(Color.WHITE);
            chart.setGridBackgroundColor(fillColor);
            chart.setDrawGridBackground(true);
            chart.setDrawBorders(true);
            chart.setPinchZoom(true);
            chart.getDescription().setEnabled(false);


            YAxis yAxis = chart.getAxisLeft();
            yAxis.setDrawAxisLine(true);



            databaseHelper = new SpinnerDatabaseHelper(this, "Sellerlocation_v01.db", null, 1);


            fillSpinner(this, autoTvDistrict, "Towns", "District", "" );

            nameOfTown = autoTvDistrict.getText().toString();

            //Log.d("Town name", "Selected Town = " + nameOfTown);


            viewGraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //setData();

                    String district = autoTvDistrict.getText().toString();
                    if (district.trim().isEmpty()) {
                        Toast.makeText(GraphicsWeeklyActivity.this, "Chagua mji au wilaya husika", Toast.LENGTH_SHORT).show();
                    } else {


                        String townSell = autoTvDistrict.getText().toString();


                        //yesterday
                        dayZero = new Date();
                        Calendar eightDaysAgo = Calendar.getInstance();
                        eightDaysAgo.setTime(dayZero);
                        eightDaysAgo.add(Calendar.DATE, -8);
                        dayZero = eightDaysAgo.getTime();

                        //yesterday
                        dayOne = new Date();
                        Calendar sevendaysago = Calendar.getInstance();
                        sevendaysago.setTime(dayOne);
                        sevendaysago.add(Calendar.DATE, -7);
                        dayOne = sevendaysago.getTime();

                        //yesterday
                        dayTwo = new Date();
                        Calendar sixdaysago = Calendar.getInstance();
                        sixdaysago.setTime(dayTwo);
                        sixdaysago.add(Calendar.DATE, -6);
                        dayTwo = sixdaysago.getTime();

                        //yesterday
                        dayThree = new Date();
                        Calendar fiveDaysAgo = Calendar.getInstance();
                        fiveDaysAgo.setTime(dayThree);
                        fiveDaysAgo.add(Calendar.DATE, -5);
                        dayThree = fiveDaysAgo.getTime();
                        //yesterday
                        dayFour = new Date();
                        Calendar fourDaysAgo = Calendar.getInstance();
                        fourDaysAgo.setTime(dayFour);
                        fourDaysAgo.add(Calendar.DATE, -4);
                        dayFour = fourDaysAgo.getTime();
                        //yesterday
                        dayFive = new Date();
                        Calendar threeDaysAgo = Calendar.getInstance();
                        threeDaysAgo.setTime(dayFive);
                        threeDaysAgo.add(Calendar.DATE, -3);
                        dayFive = threeDaysAgo.getTime();
                        //yesterday
                        daySix = new Date();
                        Calendar twoDaysago = Calendar.getInstance();
                        twoDaysago.setTime(daySix);
                        twoDaysago.add(Calendar.DATE, -2);
                        daySix = twoDaysago.getTime();

                        //yesterday
                        daySeven = new Date();
                        Calendar oneDayAgo = Calendar.getInstance();
                        oneDayAgo.setTime(daySeven);
                        oneDayAgo.add(Calendar.DATE, -1);
                        daySeven = oneDayAgo.getTime();

                        //yesterday
                        dayEight = new Date();
                        Calendar zeroDayAgo = Calendar.getInstance();
                        zeroDayAgo.setTime(daySeven);
                        zeroDayAgo.add(Calendar.DATE, -0);
                        dayEight = zeroDayAgo.getTime();


                        ArrayList<Entry> dataPoints = new ArrayList<>();


                        Query query_dayone = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                                .whereGreaterThan("today", dayZero).whereLessThan("today", dayTwo);

                        query_dayone.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    int price = 0;
                                    int count = 0;
                                    //int index = 0;

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                                        assert totalPrices != null;
                                        int total = Integer.parseInt(totalPrices);
                                        price += total;
                                        count++;

                                        dayOne = documentSnapshot.getDate("today");



                                    }

                                    int total = 0;
                                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                        String totalKukus = documentqty.getString("numberOfProduct");
                                        assert totalKukus != null;
                                        int qty = Integer.parseInt(totalKukus);

                                        total += qty;

                                        //Log.d(TAG, String.valueOf(total));
                                        //Log.d("DATED", "Formateddate "+ dayOneF);


                                        avgPriceDayOne = price / count;



                                    }

                                }

                            }


                        });

                        Query query_daytwo = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                                .whereGreaterThan("today", dayOne).whereLessThan("today", dayThree);

                        query_daytwo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    int price = 0;
                                    int count = 0;
                                    //int index = 0;

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                                        assert totalPrices != null;
                                        int total = Integer.parseInt(totalPrices);
                                        price += total;
                                        count++;

                                       dayTwo = documentSnapshot.getDate("today");


                                    }

                                    int total = 0;
                                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                        String totalKukus = documentqty.getString("numberOfProduct");
                                        assert totalKukus != null;
                                        int qty = Integer.parseInt(totalKukus);

                                        total += qty;

                                        //Log.d(TAG, String.valueOf(total));


                                        avgPriceDayTwo = price / count;



                                    }
                                }
                            }


                        });
                        //-----------------------------------
                        Query query_daythree = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                                .whereGreaterThan("today", dayTwo).whereLessThan("today", dayFour);

                        query_daythree.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    int price = 0;
                                    int count = 0;
                                    //int index = 0;

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                                        assert totalPrices != null;
                                        int total = Integer.parseInt(totalPrices);
                                        price += total;
                                        count++;

                                        dayThree = documentSnapshot.getDate("today");

                                    }

                                    int total = 0;
                                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                        String totalKukus = documentqty.getString("numberOfProduct");
                                        assert totalKukus != null;
                                        int qty = Integer.parseInt(totalKukus);

                                        total += qty;

                                        //Log.d(TAG, String.valueOf(total));


                                        avgPriceDayThree = price / count;



                                    }
                                }
                            }


                        });
                        //----------------------------------------------------------------

                        Query query_dayfour = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                                .whereGreaterThan("today", dayThree).whereLessThan("today", dayFive);

                        query_dayfour.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    int price = 0;
                                    int count = 0;
                                    //int index = 0;

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                                        assert totalPrices != null;
                                        int total = Integer.parseInt(totalPrices);
                                        price += total;
                                        count++;

                                        dayFour = documentSnapshot.getDate("today");

                                    }

                                    int total = 0;
                                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                        String totalKukus = documentqty.getString("numberOfProduct");
                                        assert totalKukus != null;
                                        int qty = Integer.parseInt(totalKukus);

                                        total += qty;

                                        //Log.d(TAG, String.valueOf(total));


                                        avgPriceDayFour = price / count;

                                        //dayFourF = Float.parseFloat(stringDate);



                                    }
                                }

                            }




                        });
                        //-------------------------------------------------------------

                        Query query_dayfive = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                                .whereGreaterThan("today", dayFour).whereLessThan("today", daySix);

                        query_dayfive.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    int price = 0;
                                    int count = 0;
                                    //int index = 0;

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                                        assert totalPrices != null;
                                        int total = Integer.parseInt(totalPrices);
                                        price += total;
                                        count++;

                                        dayFive = documentSnapshot.getDate("today");


                                    }

                                    int total = 0;
                                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                        String totalKukus = documentqty.getString("numberOfProduct");
                                        assert totalKukus != null;
                                        int qty = Integer.parseInt(totalKukus);

                                        total += qty;

                                        //Log.d(TAG, String.valueOf(total));


                                      avgPriceDayFive = price/count;



                                    }
                                }
                            }


                        });
                        //--------------------------------------------------------

                        Query query_daysix = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                                .whereGreaterThan("today", dayFive).whereLessThan("today", daySeven);

                        query_daysix.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    int price = 0;
                                    int count = 0;
                                    //int index = 0;

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                                        assert totalPrices != null;
                                        int total = Integer.parseInt(totalPrices);
                                        price += total;
                                        count++;

                                        daySix = documentSnapshot.getDate("today");




                                    }

                                    int total = 0;
                                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                        String totalKukus = documentqty.getString("numberOfProduct");
                                        assert totalKukus != null;
                                        int qty = Integer.parseInt(totalKukus);

                                        total += qty;

                                        //Log.d(TAG, String.valueOf(total));
                                        //list.add(buyItems.getToday());


                                        avgPriceDaySix = price / count;



                                    }
                                }
                            }


                        });
                        //-----------------------------------------------------------------------

                        Query query_dayseven = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                                .whereGreaterThan("today", daySix).whereLessThan("today", dayEight);

                        query_dayseven.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    int price = 0;
                                    int count = 0;
                                    //int index = 0;

                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                                        assert totalPrices != null;
                                        int total = Integer.parseInt(totalPrices);
                                        price += total;
                                        count++;

                                        daySeven = documentSnapshot.getDate("today");

                                    }

                                    int total = 0;
                                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                        String totalKukus = documentqty.getString("numberOfProduct");
                                        assert totalKukus != null;
                                        int qty = Integer.parseInt(totalKukus);

                                        total += qty;

                                        //Log.d(TAG, String.valueOf(total));


                                        avgPriceDaySeven = price / count;


                                    }
                                } else {
                                    Log.d("DebugTag", "Day Seven Failed: ", task.getException());
                                }
                            }


                        });

                        ArrayList<String> xAxisLabels = new ArrayList<>();
                        xAxisLabels.add(simpleDateFormat.format(dayOne));
                        xAxisLabels.add(simpleDateFormat.format(dayTwo));
                        xAxisLabels.add(simpleDateFormat.format(dayThree));
                        xAxisLabels.add(simpleDateFormat.format(dayFour));
                        xAxisLabels.add(simpleDateFormat.format(dayFive));
                        xAxisLabels.add(simpleDateFormat.format(daySix));
                        xAxisLabels.add(simpleDateFormat.format(daySeven));


                        //Log.d("AxisLabels", "labels "+ xAxisLabels);

                        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisLabels));

                        //dataPoints.add(new Entry(list, avgPriceDayOne));

                        //--------------------------------------------------------------------------------------------

                        dataPoints.add(new Entry(0, avgPriceDayOne));
                        dataPoints.add(new Entry(1, avgPriceDayTwo));
                        dataPoints.add(new Entry(2, avgPriceDayThree));
                        dataPoints.add(new Entry(3, avgPriceDayFour));
                        dataPoints.add(new Entry(4, avgPriceDayFive));
                        dataPoints.add(new Entry(5, avgPriceDaySix));
                        dataPoints.add(new Entry(6, avgPriceDaySeven));

                        LineDataSet datePriceDataSet = new LineDataSet(dataPoints, "Mwenendo wa bei (TSh) wiki iliyopita "+townSell);
                        LineData priceData = new LineData(datePriceDataSet);
                        datePriceDataSet.setColor(Color.RED);
                        datePriceDataSet.setLineWidth(2f);
                        datePriceDataSet.setCircleColor(Color.BLACK);
                        chart.setData(priceData);
                                }
                            }
                        });

            ArrayList<Integer> prices = new ArrayList<>();

            prices.add(avgPriceDayOne);
            prices.add(avgPriceDayTwo);
            prices.add(avgPriceDayThree);
            prices.add(avgPriceDayFour);
            prices.add(avgPriceDayFive);
            prices.add(avgPriceDaySix);
            prices.add(avgPriceDaySeven);

            Log.d("AxisValues", "ValuesY "+ prices);



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

    }