package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GraphicsActivity extends AppCompatActivity {

    //GraphView graphView;
    //Context context;
    //String townValue;
    String stringDate;
    private LineChart chart;
    //Date date;
    private  final  int fillColor = Color.argb(255, 255, 255, 255);

    SpinnerDatabaseHelper databaseHelper;
    String nameOfTown;
    private AutoCompleteTextView autoTvDistrict;

    public Toolbar toolbar;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private final int i = 0;
    Button viewGraph;
    BuyItems buyItems;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Date date = new Date(System.currentTimeMillis());
    //private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
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
        setContentView(R.layout.activity_graphics);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //progressBar = findViewById(R.id.progress_bar);
        autoTvDistrict = findViewById(R.id.districtTextView);
        viewGraph = findViewById(R.id.btnViewGraph);

        chart = findViewById(R.id.lineChart);
        chart.setBackgroundColor(Color.WHITE);
        chart.setGridBackgroundColor(fillColor);
        chart.setDrawGridBackground(true);
        chart.setDrawBorders(true);
        chart.setPinchZoom(true);
        chart.getDescription().setEnabled(false);




        ArrayList<BuyItems> list = new ArrayList<BuyItems>();
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float stringDate) {
                if(stringDate < 0 || stringDate > list.size() -1)
                    return "";
                return simpleDateFormat.format(list.get((int)stringDate).getToday());
            }
        });

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawAxisLine(true);

        //graphView.addSeries(series);
        //nameOfTown = autoTvDistrict.getText().toString();


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
                    Toast.makeText(GraphicsActivity.this, "Chagua mji au wilaya husika", Toast.LENGTH_SHORT).show();
                } else {


                    String townSell = autoTvDistrict.getText().toString();

                    Date today = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(today);
                    calendar.add(Calendar.DATE, -1);
                    today = calendar.getTime();

                    //yesterday
                    Date wkago = new Date();
                    Calendar calendarwkago = Calendar.getInstance();
                    calendarwkago.setTime(wkago);
                    calendarwkago.add(Calendar.DATE, -8);
                    wkago = calendarwkago.getTime();

                    //List<DataPoint> dataPoints = new ArrayList<>();
                    Query query = db.collectionGroup("postId").whereEqualTo("townOfSeller", townSell)
                            .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                            .whereGreaterThan("today", wkago).whereLessThan("today", today);


                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                Log.d("Town name", "Selected Town = " + townSell);

                                int i = -1;
                                autoTvDistrict.setText("");

                                ArrayList<Entry> date_PriceList = new ArrayList<Entry>();
                                //List<Integer> price_list = new ArrayList<>();

                                //Da<> set1 = new ArrayList<>();

                                for (QueryDocumentSnapshot docSnapshot : task.getResult()) {
                                    //Map data = docSnapshot.getData();

                                    if (docSnapshot.exists()) {
                                        BuyItems buyItems = docSnapshot.toObject(BuyItems.class);
                                        list.add(buyItems);

                                        //Date date = docSnapshot.getDate("today");
                                        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/", Locale.getDefault(Locale.Category.FORMAT));
                                        //assert date != null;
                                        //String stringDate = simpleDateFormat.format(date);
                                        //float dateF = Float.parseFloat(stringDate);

                                        i++;

                                        date_PriceList.add(new Entry(i, buyItems.getPriceOfProduct()));
                                    }
                                }

                                LineDataSet datePriceDataSet = new LineDataSet(date_PriceList, "Mwenendo wa bei (TSh) wiki iliyopita "+townSell);
                                LineData priceData = new LineData(datePriceDataSet);
                                datePriceDataSet.setColor(Color.RED);
                                datePriceDataSet.setLineWidth(2f);
                                datePriceDataSet.setCircleColor(Color.BLACK);
                                chart.setData(priceData);
                            }
                        }
                    });

                   /*                 int price = Integer.parseInt(String.valueOf(docSnapshot.get("priceOfProduct")));
                                    //dataPoints.add(new DataPoint(index, quantity));
                                    Date date = docSnapshot.getDate("today");
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault(Locale.Category.FORMAT));
                                    assert date != null;
                                    String stringDate = simpleDateFormat.format(date);
                                    //date_list.add(stringDate);
                                    //price_list.add(price);
                                    //index++;

                                    date_PriceList.add(new Entry(stringDate, price));

                                    for (int i = 0; i < date_list.size(); i++) {

                                        ArrayList<ILineDataSet> datasets = new ArrayList<>();
                                        //int price = Integer.parseInt(String.valueOf(docSnapshot.get("priceOfProduct")));//date_list.add(i, stringDate);

                                        datasets.add(date_list, price_list)
                                    }

                                        //series = new LineGraphSeries<>(new DataPoint[]{new DataPoint(date, price)
                                                //        series = new LineGraphSeries<>(dataPoints.toArray(new DataPoint[dataPoints.size()]));
                                        LineDataSet set1;

                                    if(chart.getData()!=null && chart.getData().getDataSetCount()>0){





                                        });


                                    }


                                    series.appendData(new DataPoint(date, price), true, 25);
                                    series.setDrawBackground(false);
                                    graphView.addSeries(series);
                                    graphView.setTitle("Wiki iliyopita Mji wa "+ townSell);
                                    graphView.setTitleColor(R.color.Orange);
                                    graphView.setTitleTextSize(16);


                                    graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                                        @Override
                                        public String formatLabel(double date, boolean isValueX) {
                                            if (isValueX) {
                                                Format formatter = new SimpleDateFormat("yy-MM-dd", Locale.getDefault(Locale.Category.FORMAT));
                                                return formatter.format(date);
                                            }
                                            return super.formatLabel(date, isValueX);
                                        }


                                    });

                                    graphView.getGridLabelRenderer().setHorizontalLabelsAngle(90);



                                }
                            }
                        }
                    });*/
                }
            }
        });


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