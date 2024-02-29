package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.Locale;

public class HybridDailyGraphicsActivity extends AppCompatActivity {



    //GraphView graphView;
    //Context context;
    //String townValue;
    private int avgPriceHrOne;
    private int avgPriceHrTwo;
    private int avgPriceHrThree;
    private int avgPriceHrFour;
    private int avgPriceHrFive;
    private int avgPriceHrSix;
    private int avgPriceHrSeven;
    private int avgPriceHrEight;

    //private Ti hour0;
    private Date hour1, hr1;
    private Date hour2;
    private Date hour3;
    private Date hour4;
    private Date hour5;
    private Date hour6;
    private Date hour7;
    private Date hour8;

    private Date hour9;

    private LineChart chart;
    //Date date;
    private  final  int fillColor = Color.argb(255, 255, 255, 255);

    SpinnerDatabaseHelper databaseHelper;
    String nameOfTown;
    private AutoCompleteTextView autoTvDistrict;

    public Toolbar toolbar;
    Button viewGraph;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.getDefault(Locale.Category.FORMAT));

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
        setContentView(R.layout.activity_local_daily_graphics);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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


        viewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //setData();

                String district = autoTvDistrict.getText().toString();
                if (district.trim().isEmpty()) {
                    Toast.makeText(HybridDailyGraphicsActivity.this, "Chagua mji au wilaya husika", Toast.LENGTH_SHORT).show();
                } else {


                    String townSell = autoTvDistrict.getText().toString();


                    //24hrs past
                    hour1 = new Date();
                    hour1.setTime(System.currentTimeMillis()-(24*60*60*1000));
                    //Calendar twentyFourHrsAgo = Calendar.getInstance();
                    //twentyFourHrsAgo.setTime(hour1);
                    //hour1 =  twentyFourHrsAgo.getTime();
                    Log.d("TAG", "Imebuma " + hour1);

                    //21hrs past
                    hour2 = new Date();
                    hour2.setTime(System.currentTimeMillis()-(21*60*60*1000));
                    //Calendar twentOneHrsAgo = Calendar.getInstance();
                    //twentOneHrsAgo.setTime(hour2);
                    //hour2 =  twentOneHrsAgo.getTime();
                    Log.d("TAG", "Imebuma " + hour2);

                    Date hourDifference;
                    hourDifference = new Date();
                    hourDifference.setTime(System.currentTimeMillis());

                    Date hrs;
                    hrs = new Date();
                    hrs.setTime(System.currentTimeMillis()-(2*60*60*1000));

                    Long tm;

                    Long hourDiff;
                    //tm = new Date();
                    //tm.setTime(System.currentTimeMillis());
                    //Calendar curr = Calendar.getInstance();
                    //tm=curr.getTime();

                    tm = hrs.getTime();
                    hourDiff = hourDifference.getTime();

                    Log.d("TAG", "Imebuma " + tm + ", "+ hourDiff);

                    //18hrs past
                    hour3 = new Date();
                    hour3.setTime(System.currentTimeMillis()-(18*60*60*1000));
                    //Calendar eighteenHrsAgo = Calendar.getInstance();
                    //eighteenHrsAgo.setTime(hour3);
                    //hour3 = eighteenHrsAgo.getTime();
                    Log.d("TAG", "Imebuma " + hour3);

                    //15hrs past
                    hour4 = new Date();
                    hour4.setTime(System.currentTimeMillis()-(15*60*60*1000));
                    //Calendar fifteenHrsAgo = Calendar.getInstance();
                    //fifteenHrsAgo.setTime(hour4);
                    //hour4 = fifteenHrsAgo.getTime();
                    Log.d("TAG", "Imebuma " + hour4);

                    //12hrs past
                    hour5 = new Date();
                    hour5.setTime(System.currentTimeMillis()-(12*60*60*1000));
                    //Calendar twelveHrsAgo = Calendar.getInstance();
                    // twelveHrsAgo.setTime(hour5);
                    //hour5 = twelveHrsAgo.getTime();

                    //9hrs past
                    hour6 = new Date();
                    hour6.setTime(System.currentTimeMillis()-(9*60*60*1000));
                    //Calendar nineHrsAgo = Calendar.getInstance();
                    //nineHrsAgo.setTime(hour6);
                    //hour6 = nineHrsAgo.getTime();

                    //6hrs past
                    hour7 = new Date();
                    hour7.setTime(System.currentTimeMillis()-(6*60*60*1000));
                    //Calendar sixHrsAgo = Calendar.getInstance();
                    //sixHrsAgo.setTime(hour7);
                    //hour7 = sixHrsAgo.getTime();

                    //3hrs past
                    hour8 = new Date();
                    hour8.setTime(System.currentTimeMillis()-(3*60*60*1000));
                    //Calendar threeHrsAgo = Calendar.getInstance();
                    //threeHrsAgo.setTime(hour8);
                    //hour8 = threeHrsAgo.getTime();

                    //0hrs past
                    hour9 = new Date();
                    hour9.setTime(System.currentTimeMillis());
                    //Calendar zeroHrsAgo = Calendar.getInstance();
                    //zeroHrsAgo.setTime(hour9);
                    //hour9 = zeroHrsAgo.getTime();


                    ArrayList<Entry> dataPoints = new ArrayList<>();

                    //first3Hours

                    Query query_dayone = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour1).whereLessThan("today", hour2);//<<

                    query_dayone.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                int price = 0;
                                int count = 0;
                                //int index = 0;

                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    Log.d("TAG", "Price " + documentSnapshot.getData());

                                    String totalPrices = documentSnapshot.getString("priceOfProduct");
                                    assert totalPrices != null;
                                    int total = Integer.parseInt(totalPrices);
                                    price += total;
                                    count++;

                                    hr1 = documentSnapshot.getDate("today");

                                    Log.d("TAG", "Tarehe " + hr1);


                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));
                                    //Log.d("DATED", "Formateddate "+ dayOneF);


                                    avgPriceHrOne = price / count;

                                }

                            }

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "Imebuma ", e);
                            e.printStackTrace();
                        }
                    });

                    Query query_daytwo = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour2).whereLessThan("today", hour3);

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

                                    hour2 = documentSnapshot.getDate("today");

                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));

                                    avgPriceHrTwo = price / count;

                                }
                            }
                        }

                    });
                    //-----------------------------------
                    Query query_daythree = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour3).whereLessThan("today", hour4);

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

                                    hour3 = documentSnapshot.getDate("today");

                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));

                                    avgPriceHrThree = price / count;

                                }
                            }
                        }
                    });
                    //----------------------------------------------------------------

                    Query query_dayfour = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour4).whereLessThan("today", hour5);

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

                                    hour4 = documentSnapshot.getDate("today");

                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));


                                    avgPriceHrFour = price / count;

                                    //dayFourF = Float.parseFloat(stringDate);

                                }
                            }

                        }

                    });
                    //-------------------------------------------------------------

                    Query query_dayfive = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour5).whereLessThan("today", hour6);

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

                                    hour5 = documentSnapshot.getDate("today");

                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));


                                    avgPriceHrFive = price/count;

                                }
                            }
                        }


                    });
                    //--------------------------------------------------------

                    Query query_daysix = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour6).whereLessThan("today", hour7);

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

                                    hour6 = documentSnapshot.getDate("today");
                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));
                                    //list.add(buyItems.getToday());


                                    avgPriceHrSix = price / count;

                                }
                            }
                        }

                    });
                    //-----------------------------------------------------------------------

                    Query query_dayseven = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour7).whereLessThan("today", hour8);

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

                                    hour7 = documentSnapshot.getDate("today");

                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));


                                    avgPriceHrSeven = price / count;


                                }
                            } else {
                                Log.d("DebugTag", "Day Seven Failed: ", task.getException());
                            }
                        }

                    });

                    Query query_dayseight = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Chotara")
                            .whereGreaterThan("today", hour8).whereLessThan("today", hour9);

                    query_dayseight.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                                    hour8 = documentSnapshot.getDate("today");

                                }

                                int total = 0;
                                for (QueryDocumentSnapshot documentqty : task.getResult()) {
                                    String totalKukus = documentqty.getString("numberOfProduct");
                                    assert totalKukus != null;
                                    int qty = Integer.parseInt(totalKukus);

                                    total += qty;

                                    //Log.d(TAG, String.valueOf(total));


                                    avgPriceHrEight = price / count;

                                }
                            } else {
                                Log.d("DebugTag", "Day Seven Failed: ", task.getException());
                            }
                        }

                    });

                    ArrayList<String> xAxisLabels = new ArrayList<>();
                    xAxisLabels.add(simpleDateFormat.format(hour1));
                    xAxisLabels.add(simpleDateFormat.format(hour2));
                    xAxisLabels.add(simpleDateFormat.format(hour3));
                    xAxisLabels.add(simpleDateFormat.format(hour4));
                    xAxisLabels.add(simpleDateFormat.format(hour5));
                    xAxisLabels.add(simpleDateFormat.format(hour6));
                    xAxisLabels.add(simpleDateFormat.format(hour7));
                    xAxisLabels.add(simpleDateFormat.format(hour8));


                    //Log.d("AxisLabels", "labels "+ xAxisLabels);

                    chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisLabels));

                    //dataPoints.add(new Entry(list, avgPriceDayOne));

                    //--------------------------------------------------------------------------------------------

                    dataPoints.add(new Entry(0, avgPriceHrOne));
                    dataPoints.add(new Entry(1, avgPriceHrTwo));
                    dataPoints.add(new Entry(2, avgPriceHrThree));
                    dataPoints.add(new Entry(3, avgPriceHrFour));
                    dataPoints.add(new Entry(4, avgPriceHrFive));
                    dataPoints.add(new Entry(5, avgPriceHrSix));
                    dataPoints.add(new Entry(6, avgPriceHrSeven));
                    dataPoints.add(new Entry(7, avgPriceHrEight));

                    LineDataSet datePriceDataSet = new LineDataSet(dataPoints, "Mwenendo wa bei (TSh) 24Hr "+townSell);
                    LineData priceData = new LineData(datePriceDataSet);
                    datePriceDataSet.setColor(Color.RED);
                    datePriceDataSet.setLineWidth(2f);
                    datePriceDataSet.setCircleColor(Color.BLACK);
                    chart.setData(priceData);
                }
            }
        });

        ArrayList<Integer> prices = new ArrayList<>();

        prices.add(avgPriceHrOne);
        prices.add(avgPriceHrTwo);
        prices.add(avgPriceHrThree);
        prices.add(avgPriceHrFour);
        prices.add(avgPriceHrFive);
        prices.add(avgPriceHrSix);
        prices.add(avgPriceHrSeven);
        prices.add(avgPriceHrEight);

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