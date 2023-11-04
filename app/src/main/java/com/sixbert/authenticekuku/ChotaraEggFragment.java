package com.sixbert.authenticekuku;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class ChotaraEggFragment extends Fragment implements SearchView.OnQueryTextListener{
    private final List<BuyItems>  buyItem = new ArrayList<>();
    static final String TAG = "SearchBox";
    private RecyclerView mRecyclerView;
    private BuyItemAdapter adapter;
    public ChotaraEggFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chotara_egg, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView_chotara_egg);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        FirebaseFirestore mUserDatabase = FirebaseFirestore.getInstance();

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



        mUserDatabase
                .collectionGroup("postId")
                .whereEqualTo("typeOfItem", "Mayai Chotara (Trei)")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>"+ document.getData());
                                if (document!=null) {
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        adapter = new BuyItemAdapter(getContext(), buyItem);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public void  onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filtermenu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        ImageView searchIcon =searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_earch));
        EditText editText = searchView .findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        editText.setHint("Tafuta...");
        editText.setHintTextColor(Color.WHITE);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setActionView(searchView);


        searchView.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem menuItem) {
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