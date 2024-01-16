package com.sixbert.authenticekuku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;



public class WeeklyStatsFragment extends Fragment {


    private static final String TAG = "WeekResults";

    TextView totalLocalChicken, avgPrices, broileravgPrices, totalbroilerChicken,
            totalhybridChicken, avghybrPrices,totalLocalEggs,avglocaleggPrices, totalLayerEggs,
            avglayersEggPrices, totalHybridEggs, avghybrdEggPrices;



    public WeeklyStatsFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View  rootView = inflater.inflate(R.layout.fragment_weekly_stats, container, false);

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

       FirebaseFirestore mdb = FirebaseFirestore.getInstance();

       Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -1);
        today = calendar.getTime();


        Date wkago = new Date();
        Calendar calendarwkago = Calendar.getInstance();
        calendarwkago.setTime(wkago);
        calendarwkago.add(Calendar.DATE, -8);
        wkago = calendarwkago.getTime();


        //List<DataPoint> datapoints = new ArrayList<>();


       Query query = mdb.collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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




                        }

                        int total = 0;
                        for (QueryDocumentSnapshot documentqty : task.getResult()) {
                            String totalKukus = documentqty.getString("numberOfProduct");
                            assert totalKukus != null;
                            int qty = Integer.parseInt(totalKukus);

                            total += qty;

                            Log.d(TAG, String.valueOf(total));


                            int avg = price / count;

                            Log.d(TAG, String.valueOf(avg));

                            avgPrices.setText(String.valueOf(avg));
                            totalLocalChicken.setText(String.valueOf(total));

                            avgPrices.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //LayoutInflater inflater = (LayoutInflater) requireActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                    Intent intent = new Intent(getActivity(), GraphicsActivity.class);
                                    startActivity(intent);

                                }
                            });

                        }
                    }
            }


            });
///broiler chicken
        Query queryBroiler = mdb.collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Kuku Kisasa")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        queryBroiler.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int price = 0;
                    int count = 0;

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                        assert totalPrices != null;
                        int total = Integer.parseInt(totalPrices);
                        price += total;
                        count++;


                        //totalValue += prices;
                        Log.d(TAG, String.valueOf(price));
                    } //catch (Exception e) {

                    int total = 0;
                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                        String totalKukus = documentqty.getString("numberOfProduct");
                        assert totalKukus != null;
                        int qty = Integer.parseInt(totalKukus);
                        total += qty;

                        Log.d(TAG, String.valueOf(total));


                        int avg = price / count;

                        Log.d(TAG, String.valueOf(avg));

                        broileravgPrices.setText(String.valueOf(avg));
                        totalbroilerChicken.setText(String.valueOf(total));

                    }
                }
            }


        });
//hybrid chicken

        Query queryHyb = mdb.collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Kuku Chotara")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        queryHyb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int price = 0;
                    int count = 0;

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                        assert totalPrices != null;
                        int total = Integer.parseInt(totalPrices);
                        price += total;
                        count++;


                        //totalValue += prices;
                        Log.d(TAG, String.valueOf(price));
                    } //catch (Exception e) {

                    int total = 0;
                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                        String totalKukus = documentqty.getString("numberOfProduct");
                        assert totalKukus != null;
                        int qty = Integer.parseInt(totalKukus);
                        total += qty;

                        Log.d(TAG, String.valueOf(total));


                        int avg = price / count;

                        Log.d(TAG, String.valueOf(avg));

                        avghybrPrices.setText(String.valueOf(avg));
                        totalhybridChicken.setText(String.valueOf(total));

                    }
                }
            }


        });
//Localeggs
        Query queryLocalEggs = mdb.collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Mayai Kienyeji")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        queryLocalEggs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int price = 0;
                    int count = 0;

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                        assert totalPrices != null;
                        int total = Integer.parseInt(totalPrices);
                        price += total;
                        count++;


                        //totalValue += prices;
                        Log.d(TAG, String.valueOf(price));
                    } //catch (Exception e) {

                    int total = 0;
                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                        String totalKukus = documentqty.getString("numberOfProduct");
                        assert totalKukus != null;
                        int qty = Integer.parseInt(totalKukus);
                        total += qty;

                        Log.d(TAG, String.valueOf(total));


                        int avg = price / count;

                        Log.d(TAG, String.valueOf(avg));

                        avglocaleggPrices.setText(String.valueOf(avg));
                        totalLocalEggs.setText(String.valueOf(total));

                    }
                }
            }


        });

//Layers eggs
        Query queryLayerEggs = mdb.collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Mayai Kisasa")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        queryLayerEggs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int price = 0;
                    int count = 0;

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                        assert totalPrices != null;
                        int total = Integer.parseInt(totalPrices);
                        price += total;
                        count++;


                        //totalValue += prices;
                        Log.d(TAG, String.valueOf(price));
                    } //catch (Exception e) {

                    int total = 0;
                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                        String totalKukus = documentqty.getString("numberOfProduct");
                        assert totalKukus != null;
                        int qty = Integer.parseInt(totalKukus);
                        total += qty;

                        Log.d(TAG, String.valueOf(total));


                        int avg = price / count;

                        Log.d(TAG, String.valueOf(avg));

                        avglayersEggPrices.setText(String.valueOf(avg));
                        totalLayerEggs.setText(String.valueOf(total));

                    }
                }
            }


        });

//HybridEggs
        Query queryEggHyb = mdb.collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Mayai Chotara")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        queryEggHyb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int price = 0;
                    int count = 0;

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        String totalPrices = documentSnapshot.getString("priceOfProduct");
                        assert totalPrices != null;
                        int total = Integer.parseInt(totalPrices);
                        price += total;
                        count++;


                        //totalValue += prices;
                        Log.d(TAG, String.valueOf(price));
                    } //catch (Exception e) {

                    int total = 0;
                    for (QueryDocumentSnapshot documentqty : task.getResult()) {
                        String totalKukus = documentqty.getString("numberOfProduct");
                        assert totalKukus != null;
                        int qty = Integer.parseInt(totalKukus);
                        total += qty;

                        Log.d(TAG, String.valueOf(total));


                        int avg = price / count;

                        Log.d(TAG, String.valueOf(avg));

                        avghybrdEggPrices.setText(String.valueOf(avg));
                        totalHybridEggs.setText(String.valueOf(total));

                    }
                }
            }


        });

/////////////

        return rootView;
    }


}