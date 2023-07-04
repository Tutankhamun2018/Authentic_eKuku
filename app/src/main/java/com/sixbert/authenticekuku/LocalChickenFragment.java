package com.sixbert.authenticekuku;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;




public class LocalChickenFragment extends Fragment implements SearchView.OnQueryTextListener{

    private final List<BuyItems>  buyItem = new ArrayList<>();
    private List<BuyItems> buyAllItems;
    static final String TAG = "SearchBox";
    private RecyclerView mRecyclerView;
    private BuyItemAdapter adapter;

    //private Task<QuerySnapshot> query;
    public LocalChickenFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_local_chicken, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView_loca_chicken);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        FirebaseFirestore mUserDatabase = FirebaseFirestore.getInstance();

        //Calendar calendar = Calendar.getInstance();
        //Date currentDate = calendar.getTime();
        //Timestamp today = new Timestamp(currentDate);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.i(TAG, "today is: " + currentDate);

        mUserDatabase
                .collection("eKuku")
                .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                .whereEqualTo("today", currentDate)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>"+ document.getData());
                                if(document != null) {
                                    buyItem.add(document.toObject(BuyItems.class));
                                }
                            }
                        adapter.notifyDataSetChanged();
                           } else {
                            Log.d(TAG, "Error getting docs: ", task.getException());
                        }
                    }

                });



        return rootView;

    }
    @Override

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        /*ArrayList<BuyItems> filteredList= new ArrayList<>();

        for (BuyItems items: buyAllItems){
            filteredList.add(new BuyItems(filteredList));

        }*/
        adapter = new BuyItemAdapter(getContext(), buyItem);
        mRecyclerView.setAdapter(adapter);
    }
@Override
    public void  onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.filtermenu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(this);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                adapter.setFilter(buyItem);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<BuyItems> fileredList = filter(buyItem, newText);
        adapter.setFilter(fileredList);
        return true;
    }

    private List<BuyItems> filter(List<BuyItems> buyItems, String query){
        query = query.toLowerCase();
        final List<BuyItems> filteredList = new ArrayList<>();
        for (BuyItems buyItems1: buyItems){
            final String text = buyItems1.getTownOfSeller().toLowerCase();
            if(text.contains(query)){
                filteredList.add(buyItems1);
            }
        }
        return filteredList;
    }


}