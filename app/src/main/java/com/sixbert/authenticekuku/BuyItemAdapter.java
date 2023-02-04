package com.sixbert.authenticekuku;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.HashMap;
import java.util.List;
//import com.google.firebase.database.core.Context;

public class BuyItemAdapter extends RecyclerView.Adapter<BuyItemAdapter.ViewHolder>{

    private final int VIEW_RECYCLER = 0;
    private final int VIEW_FAB =1;
    public final Context context;
    public final List<BuyItems> buyItems;


    //private BuyItems[] buyItems;
    //private final ArrayList<BuyItems> buyItemsArrayList;
    //private BuyFragment context;
    //Constructor

    public BuyItemAdapter(Context context, List<BuyItems> buyItems){
        this.context = context;
        this.buyItems = buyItems;

        //this.buyItemsArrayList = buyItemsArrayList;
    }


    @NonNull
    @Override
    public BuyItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new BuyItemAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //holder.productType.setText(model.getTypeOfItem());
        //holder.phoneNumber.setText(model.getPhoneNumber());
       // BuyItems buyItems = buyItemsArrayList.get(i);
        holder.phoneNumber.setText(buyItems.get(position).getPhoneNumber());
        holder.numberOfProduct.setText(String.valueOf(buyItems.get(position).getNumberOfProduct()));
        holder.priceOfProduct.setText(String.valueOf(buyItems.get(position).getPriceOfProduct()));
        holder.typeOfProduct.setText(buyItems.get(position).getTypeOfItem());

        //final String documentId = getSnapshots().getSnapshot(i).getId();



    }
    public int getItemCount(){
        return buyItems.size();
    }


    //@Override
    //public int getItemCount() {
    //    return buyItemsArrayList.size();
    //}

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final View container;
        private Button btnFilter;
        TextView typeOfProduct,phoneNumber,numberOfProduct, priceOfProduct;

        public ViewHolder(View view){
            super(view);
            container = view;
            phoneNumber = view.findViewById(R.id.phoneNumber);
            typeOfProduct = view.findViewById(R.id.typ);
            numberOfProduct = view.findViewById(R.id.qty);
            priceOfProduct = view.findViewById(R.id.price);

        }

        }


  }



