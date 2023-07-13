package com.sixbert.authenticekuku;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DailyStatsFragment extends Fragment {

    private static final String TAG = "Results";
    TextView txt_date, totalLocalChicken, avgPrices, broileravgPrices, totalbroilerChicken,
            totalhybridChicken, avghybrPrices,totalLocalEggs,avglocaleggPrices, totalLayerEggs,
            avglayersEggPrices, totalHybridEggs, avghybrdEggPrices;



    public DailyStatsFragment() {
        // Required empty public constructor
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_daily_stats, container, false);


        LocalDate today = LocalDate.now();
        txt_date =rootView.findViewById(R.id.header_date);
        txt_date.setText(today.toString());
        totalLocalChicken = rootView.findViewById(R.id.totalLocalChicken);
        avgPrices = rootView.findViewById(R.id.avgPrices);
        totalbroilerChicken = rootView.findViewById(R.id.totalbroilerChicken);
        totalhybridChicken = rootView.findViewById(R.id.totalhybridChicken);
        broileravgPrices = rootView.findViewById(R.id.broileravgPrices);
        totalLocalEggs = rootView.findViewById(R.id.totalLocalEggs);
        totalLayerEggs = rootView.findViewById(R.id.totalLayerEggs);
        totalHybridEggs = rootView.findViewById(R.id.totalHybridEggs);
        avglocaleggPrices = rootView.findViewById(R.id.avglocaleggPrices);
        avglayersEggPrices = rootView.findViewById(R.id.avglayersEggPrices);
        avghybrdEggPrices = rootView.findViewById(R.id.avghybrdEggPrices);
        avghybrPrices = rootView.findViewById(R.id.avghybrPrices);


        FirebaseFirestore mUserDatabase = FirebaseFirestore.getInstance();

        //String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
       // Log.i(TAG, "today is: " +currentDate);

        Date morrow = new Date();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(morrow);
        calendar.add(java.util.Calendar.DATE, 1);
        morrow = calendar.getTime();

        //yesterday
        Date yesterday = new Date();
        java.util.Calendar calendaryesterday = java.util.Calendar.getInstance();
        calendaryesterday.setTime(yesterday);
        calendaryesterday.add(Calendar.DATE, -1);
        yesterday =calendaryesterday.getTime();

        //long currentTime = System.currentTimeMillis();
        //long twentyFourHrs = 24*60*60%1000;
        //long onedayago=currentTime-twentyFourHrs;

        Query query = mUserDatabase.collection("eKuku")
                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
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
                        Log.d(TAG, String.valueOf(totalValue));
                        Log.d(TAG, String.valueOf(count));

                    }
                }


            }
        });

        Query querybr = mUserDatabase.collection("eKuku")
                .whereEqualTo("typeOfItem", "Kuku Kisasa")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
        //AggregateQuery countQuery = query.count();
        querybr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int totalValuebr = 0;
                    int count =0;
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String totalPricesbr = documentSnapshot.getString("priceOfProduct");
                        assert totalPricesbr != null;
                        int pricesbr = Integer.parseInt(totalPricesbr);
                        totalValuebr += pricesbr;
                        //int lenbr = totalPricesbr.length();
                        count++;
                        int totalbr = 0;

                        for (QueryDocumentSnapshot documentqty : task.getResult()) {
                            String totalKukus = documentqty.getString("numberOfProduct");
                            assert totalKukus != null;
                            int qty = Integer.parseInt(totalKukus);
                            //int len = totalKukus.length();
                            //double avg =(qty/len);
                            totalbr += qty;



                            //float avg = totalValue / total;

                            //avgPrices.setText(String.valueOf(avg));

                        }
                        totalbroilerChicken.setText(String.valueOf(totalbr));
                        int avg = totalValuebr / count;
                        broileravgPrices.setText(String.valueOf(avg));//
                        //Log.d(TAG, String.valueOf(lenbr));

                    }
                }


            }
        });

        Query queryhbr = mUserDatabase.collection("eKuku")
                .whereEqualTo("typeOfItem", "Kuku Chotara")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
        //AggregateQuery countQuery = query.count();
        queryhbr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        //int len = totalPrices.length();
                        count++;
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
                        totalhybridChicken.setText(String.valueOf(total));
                        int avg = totalValue / count;
                        avghybrPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }


            }
        });

        Query querylocalEgg = mUserDatabase.collection("eKuku")
                .whereEqualTo("typeOfItem", "Mayai Kienyeji (Trei)")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
        //AggregateQuery countQuery = query.count();
        querylocalEgg.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int totalValue = 0;
                    int count = 0;
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                        assert totalPrices != null;
                        int prices = Integer.parseInt(totalPrices);
                        totalValue += prices;
                        //int len = totalPrices.length();
                        count++;
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
                        totalLocalEggs.setText(String.valueOf(total));
                        int avg = totalValue /count;
                        avglocaleggPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }


            }
        });

        Query querylayerEgg = mUserDatabase.collection("eKuku")
                .whereEqualTo("typeOfItem", "Mayai Kisasa (Trei)")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
        //AggregateQuery countQuery = query.count();
        querylayerEgg.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        //int len = totalPrices.length();
                        count++;
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
                        totalLayerEggs.setText(String.valueOf(total));
                        int avg = totalValue / count;
                        avglayersEggPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }


            }
        });

        Query queryhybrEgg = mUserDatabase.collection("eKuku")
                .whereEqualTo("typeOfItem", "Mayai Chotara (Trei)")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
        //AggregateQuery countQuery = query.count();
        queryhybrEgg.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        //int len = totalPrices.length();
                        count++;
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
                        totalHybridEggs.setText(String.valueOf(total));
                        int avg = totalValue / count;
                        avghybrdEggPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }


            }
        });



       /* query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int total = 0;
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String totalKukus = documentSnapshot.getString("numberOfProduct");
                        assert totalKukus != null;
                        int qty = Integer.parseInt(totalKukus);
                        int len = totalKukus.length();
                        //double avg =(qty/len);
                        total += qty;
                        totalLocalChicken.setText(String.valueOf(total));


                    }
                    Log.d(TAG, String.valueOf(total));

                }

            }
        });*/


        return rootView;
    }
}