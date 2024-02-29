package com.sixbert.authenticekuku;


import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Calendar;
import java.util.Date;



public class DailyStatsFragment extends Fragment {

    private static final String TAG = "Results";
    TextView txt_date, totalLocalChicken, avgPrices, broileravgPrices, totalbroilerChicken,
            totalhybridChicken, avghybrPrices,totalLocalEggs,avglocaleggPrices, totalLayerEggs,
            avglayersEggPrices, totalHybridEggs, avghybrdEggPrices;



    public DailyStatsFragment() {
        // Required empty public constructor
    }


final android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
    final int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);
    final int month = cal.get(android.icu.util.Calendar.MONTH);
    final int year = cal.get(android.icu.util.Calendar.YEAR);
    final String date = day + "/" + (month+1) + "/" + year;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_daily_stats, container, false);


        //LocalDate today = LocalDate.now();
        txt_date = rootView.findViewById(R.id.header_date);
        txt_date.setText(date);
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


        avgPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocalDailyGraphicsActivity.class);
                startActivity(intent);
            }
        });

        broileravgPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BroilerDailyGraphicsActivity.class);
                startActivity(intent);
            }
        });
        avglocaleggPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LocalEggDailyGraphicsActivity .class);
                startActivity(intent);
            }
        });
        avglayersEggPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LayersEggDailyGraphicsActivity .class);
                startActivity(intent);
            }
        });
        avghybrdEggPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),HybridEggDailyGraphicsActivity .class);
                startActivity(intent);
            }
        });
        avghybrPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),HybridDailyGraphicsActivity .class);
                startActivity(intent);
            }
        });



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


        Query query = mUserDatabase.collectionGroup("postId")
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
                            total += qty;

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

        Query querybr = mUserDatabase.collectionGroup("postId")
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
                            totalbr += qty;

                        }
                        totalbroilerChicken.setText(String.valueOf(totalbr));
                        int avg = totalValuebr / count;
                        broileravgPrices.setText(String.valueOf(avg));

                    }
                }


            }
        });

        Query queryhbr = mUserDatabase.collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Kuku Chotara")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
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
                            total += qty;

                        }
                        totalhybridChicken.setText(String.valueOf(total));
                        int avg = totalValue / count;
                        avghybrPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }

            }
        });

        Query querylocalEgg = mUserDatabase.collectionGroup("postId")
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
                            total += qty;


                        }
                        totalLocalEggs.setText(String.valueOf(total));
                        int avg = totalValue /count;
                        avglocaleggPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }


            }
        });

        Query querylayerEgg = mUserDatabase.collectionGroup("postId")
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
                            total += qty;

                        }
                        totalLayerEggs.setText(String.valueOf(total));
                        int avg = totalValue / count;
                        avglayersEggPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }


            }
        });

        Query queryhybrEgg = mUserDatabase.collectionGroup("postId")
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

                            total += qty;


                        }
                        totalHybridEggs.setText(String.valueOf(total));
                        int avg = totalValue / count;
                        avghybrdEggPrices.setText(String.valueOf(avg));//
                        Log.d(TAG, String.valueOf(totalValue));

                    }
                }

            }
        });

        return rootView;
    }
}