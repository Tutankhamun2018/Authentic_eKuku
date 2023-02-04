package com.sixbert.authenticekuku;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.core.Context;
import com.google.firebase.firestore.DocumentSnapshot;
import  com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class BuyFragment extends Fragment {

    private List<BuyItems> buyItem = new ArrayList<>();
    static final String TAG = "SearchBox";
    private BuyItemAdapter adapter;
    //RecyclerView mRecyclerView;
    Query query;
    //FloatingActionButton fabSearch;
    //private FirebaseFirestore mUserDatabase;
    //private RecyclerView recyclerView;
    //Context thisContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //thisContext = container.getContext();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buy, container, false);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);


        FirebaseFirestore mUserDatabase = FirebaseFirestore.getInstance();

        Query query = mUserDatabase
                .collection("eKuku");


        query.orderBy(Preferences.ORDER_BY).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                buyItem = task.getResult().toObjects(BuyItems.class);

                if (!Preferences.filters.isEmpty()) {
                    ArrayList<BuyItems> filteredItems = new ArrayList<>();
                    List<String> typeOfProduct = Preferences.filters.get(Filter.INDEX_TYPE).getSelected();
                    List<String> qtyOfProduct = Preferences.filters.get(Filter.INDEX_QTY).getSelected();
                    List<String> priceOfProduct = Preferences.filters.get(Filter.INDEX_PRICE).getSelected();

                    for (BuyItems buyItems : buyItem) {
                        boolean typeMatched = true;
                        if (typeOfProduct.size() > 0 && !typeOfProduct.contains(buyItems.getTypeOfItem())) {
                            typeMatched = false;
                        }
                        boolean qtyMatched = true;
                        if (qtyOfProduct.size() > 0 && !qtyOfProduct.contains(buyItems.getNumberOfProduct())) {
                            qtyMatched = false;
                        }

                        boolean priceMatched = true;

                        if (priceOfProduct.size() > 0 && priceOfProduct.contains(buyItems.getPriceOfProduct())) {
                            priceMatched = false;
                        }
                        if (typeMatched && qtyMatched && priceMatched) {
                            filteredItems.add(buyItems);
                        }
                    }

                    buyItem = filteredItems;
                }
                adapter = new BuyItemAdapter(getContext(), buyItem);
                mRecyclerView.setAdapter(adapter);
            }
        });

        //FirestoreRecyclerOptions<BuyItems> options = new FirestoreRecyclerOptions.Builder<BuyItems>()
        //      .setQuery(query, BuyItems.class)
        //    .build();

        // adapter = new BuyItemAdapter( getContext(), options);
        //mRecyclerView.setAdapter(adapter);
        //You cannot add a button (onClick) in a fragment but rather in an adapter
        FloatingActionButton fabFilter = rootView.findViewById(R.id.fab_filter);
        fabFilter.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
              startActivity(intent);
            }
       });


        return rootView;

    }
}



   /* @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }*/


          /*  @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Object model) {

            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        }
        //searchField = rootView.findViewById(R.id.search_field);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        //ImageButton searchBtn = rootView.findViewById(R.id.image_button);
        //mUserDatabase = FirebaseFirestore.getInstance();
        //searchField = rootView.findViewById(R.id.search_field);
        buyItemsArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //query = db.collection("products");
        //initControls();

        buyItemAdapter = new BuyItemAdapter(buyItemsArrayList, this);
        recyclerView.setAdapter(buyItemAdapter);


        /*searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchField.getText() + "";
                Toast.makeText(getActivity(), "Started Search", Toast.LENGTH_SHORT).show();
                firebaseBuySearch();
            }

        mUserDatabase = FirebaseFirestore.getInstance();

        mUserDatabase.collection("eKuku").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list){
                                BuyItems buyItems = d.toObject(BuyItems.class);

                                buyItemsArrayList.add(buyItems);*/

                      /*      }

                            buyItemAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to get the data", Toast.LENGTH_SHORT).show();
                    }
                });


        return rootView;
    }*/

/*
    @Override
    public void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firestoreRecyclerAdapter.stopListening();
    }*/





