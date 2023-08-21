package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class GraphActivity extends AppCompatActivity {

    private static final String TAG = "Graphics";
    GraphView graphView;
    LineGraphSeries <DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graphView =findViewById(R.id.graphView);

        FirebaseFirestore mdb = FirebaseFirestore.getInstance();

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

        //Date currentDate = calendar.getTime();
        //Timestamp today = new Timestamp(currentDate);

        //long todaysDate = System.currentTimeMillis()-24*60*60*1000;
        //long currentTime = System.currentTimeMillis()-todaysDate;
        //long sevenDaysInMillis = 7*24*60*60*1000;
        // long sevenDaysAgo = currentTime-sevenDaysInMillis;

        List<DataPoint> datapoints = new ArrayList<>();



        Query query = mdb.collection("eKuku")
                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                              @Override
                                              public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                  if (task.isSuccessful()) {
                                                      int index = 0;
                                                      List<String> date_list = new ArrayList<>();
                                                      List<Integer> price_list = new ArrayList<>();

                                                      for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                          int price = Integer.parseInt(String.valueOf(documentSnapshot.get("priceOfProduct")));
                                                          int qty = Integer.parseInt(String.valueOf(documentSnapshot.get("numberOfProduct")));
                                                          Date date = documentSnapshot.getDate("today");
                                                          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault(Locale.Category.FORMAT));
                                                          assert date != null;
                                                          String stringDate = simpleDateFormat.format(date);
                                                          date_list.add(stringDate);
                                                          price_list.add(price);



                                                          //Map data = documentSnapshot.getData();

                                                          //Double productPrice = (Double) data.get("priceOfProduct");
                                                          //datapoints.add(new DataPoint(productPrice, date));
                                                          //date++;


                                                          //datapoints.add(new DataPoint(Double.parseDouble(stringDate), price));

                                                          Log.d(TAG, stringDate);
                                                          Log.d(TAG, String.valueOf(price));

                                                          for (int i =0; i< date_list.size(); i++) {

                                                              series = new LineGraphSeries<>(new DataPoint[]{new DataPoint(date, price)

                                                              });
                                                          }


                                                          series.appendData(new DataPoint(date, price), true, 25);
                                                          series.setDrawBackground(true);
                                                          graphView.addSeries(series);
                                                          graphView.setTitle("Takwimu za Wiki");
                                                          graphView.setTitleColor(R.color.Orange);
                                                          graphView.setTitleTextSize(16);


                                                          graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                                                              @Override
                                                              public String formatLabel(double date, boolean isValueX) {
                                                                  if(isValueX){
                                                                      Format formatter = new SimpleDateFormat("yy-MM-dd",Locale.getDefault(Locale.Category.FORMAT));
                                                                      return formatter.format(date);
                                                                  }
                                                                  return super.formatLabel(date, isValueX);
                                                              }



                                                          });

                                                          graphView.getGridLabelRenderer().setHorizontalLabelsAngle(90);

                                                      }

                                                  }
                                              }
                                          });






                    }
}