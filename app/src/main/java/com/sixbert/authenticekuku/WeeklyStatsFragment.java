package com.sixbert.authenticekuku;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeeklyStatsFragment extends Fragment {

    //public static @NonNull Timestamp now();

    private static final String TAG = "WeekResults";

    TextView txt_date, totalLocalChicken, avgPrices, broileravgPrices, totalbroilerChicken,
            totalhybridChicken, avghybrPrices,totalLocalEggs,avglocaleggPrices, totalLayerEggs,
            avglayersEggPrices, totalHybridEggs, avghybrdEggPrices;



    public WeeklyStatsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View  rootView = inflater.inflate(R.layout.fragment_weekly_stats, container, false);

        //txt_date =rootView.findViewById(R.id.header_date);
        //txt_date.setText(today.toString());
        totalLocalChicken = rootView.findViewById(R.id.totalLocalChicken);
        avgPrices = rootView.findViewById(R.id.avgPrices);
        //totalbroilerChicken = rootView.findViewById(R.id.totalbroilerChicken);
        //totalhybridChicken = rootView.findViewById(R.id.totalhybridChicken);
        //broileravgPrices = rootView.findViewById(R.id.broileravgPrices);
        //totalLocalEggs = rootView.findViewById(R.id.totalLocalEggs);
        //totalLayerEggs = rootView.findViewById(R.id.totalLayerEggs);
        //totalHybridEggs = rootView.findViewById(R.id.totalHybridEggs);
        //avglocaleggPrices = rootView.findViewById(R.id.avglocaleggPrices);
        //avglayersEggPrices = rootView.findViewById(R.id.avglayersEggPrices);
        //avghybrdEggPrices = rootView.findViewById(R.id.avghybrdEggPrices);
        //avghybrPrices = rootView.findViewById(R.id.avghybrPrices);

       FirebaseFirestore mUserDatabase = FirebaseFirestore.getInstance();

        //String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        // Log.i(TAG, "today is: " +currentDate);



        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date startDate = calendar.getTime();

        Timestamp startTimeStamp = new Timestamp(startDate);
        Timestamp endTimeStamp = Timestamp.now();

        //Date daterange = startDate-endTimeStamp;

        Query query = mUserDatabase.collection("eKuku")
                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                .whereGreaterThan("date", startTimeStamp).whereLessThan("date",endTimeStamp);
        //AggregateQuery countQuery = query.count();
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int totalValue = 0;
                    int count =0;
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                        assert totalPrices != null;
                        int prices = Integer.parseInt(totalPrices);
                        totalValue += prices;
                        count++;
                        //int len = totalPrices.length();
                        int total = 0;
                        for (QueryDocumentSnapshot documentqty : task.getResult()) {
                            String totalKukus = documentqty.getString("numberOfProduct");
                            assert totalKukus != null;
                            int qty = Integer.parseInt(totalKukus);
                            //int len = totalKukus.length();
                            //double avg =(qty/len);
                            total += qty;



                            //float avg = totalValue / total;

                            //avgPrices.setText(String.valueOf(avg));

                        }
                        totalLocalChicken.setText(String.valueOf(total));

                        int avg = totalValue / count;
                        avgPrices.setText(String.valueOf(avg));//



                    }

                    Log.d(TAG, String.valueOf(totalValue));
                    Log.d(TAG, String.valueOf(count));
                }



                Log.d(TAG, String.valueOf(startTimeStamp));
                Log.d(TAG, String.valueOf(endTimeStamp));


            }
        });

        return rootView;
    }


}