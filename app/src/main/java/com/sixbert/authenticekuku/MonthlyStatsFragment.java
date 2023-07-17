package com.sixbert.authenticekuku;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Calendar;
import java.util.Date;


public class MonthlyStatsFragment extends Fragment {

    //public static @NonNull Timestamp now();

    private static final String TAG = "MonthResults";

    TextView totalLocalChicken, avgPrices, broileravgPrices, totalbroilerChicken,
            totalhybridChicken, avghybrPrices,totalLocalEggs,avglocaleggPrices, totalLayerEggs,
            avglayersEggPrices, totalHybridEggs, avghybrdEggPrices;



    public MonthlyStatsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  rootView = inflater.inflate(R.layout.fragment_montly_stats, container, false);

        //txt_date =rootView.findViewById(R.id.header_text);
        //txt_date.setText(today.toString());
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

        //yesterday
        Date wkago = new Date();
        Calendar calendarwkago = Calendar.getInstance();
        calendarwkago.setTime(wkago);
        calendarwkago.add(Calendar.DATE, -31);
        wkago = calendarwkago.getTime();

        //Date currentDate = calendar.getTime();
        //Timestamp today = new Timestamp(currentDate);

        //long todaysDate = System.currentTimeMillis()-24*60*60*1000;
        //long currentTime = System.currentTimeMillis()-todaysDate;
        //long sevenDaysInMillis = 7*24*60*60*1000;
        // long sevenDaysAgo = currentTime-sevenDaysInMillis;

        Query query = mdb.collection("eKuku")
                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                .whereGreaterThan("today", wkago).whereLessThan("today", today);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        //int len = totalKukus.length();
                        //double avg =(qty/len);
                        total += qty;

                        Log.d(TAG, String.valueOf(total));


                        int avg = price / count;

                        Log.d(TAG, String.valueOf(avg));

                        avgPrices.setText(String.valueOf(avg));
                        totalLocalChicken.setText(String.valueOf(total));

                    }
                }
            }


        });
///broiler chicken
        Query queryBroiler = mdb.collection("eKuku")
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
                        //int len = totalKukus.length();
                        //double avg =(qty/len);
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

        Query queryHyb = mdb.collection("eKuku")
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
                        //int len = totalKukus.length();
                        //double avg =(qty/len);
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
        Query queryLocalEggs = mdb.collection("eKuku")
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
                        //int len = totalKukus.length();
                        //double avg =(qty/len);
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
        Query queryLayerEggs = mdb.collection("eKuku")
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
                        //int len = totalKukus.length();
                        //double avg =(qty/len);
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
        Query queryEggHyb = mdb.collection("eKuku")
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
                        //int len = totalKukus.length();
                        //double avg =(qty/len);
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