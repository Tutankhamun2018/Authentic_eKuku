package com.sixbert.authenticekuku;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.annotations.NonNull;


public class BuyItemAdapter extends RecyclerView.Adapter<BuyItemAdapter.ViewHolder>
 {


    public final Context context;
    private List<BuyItems> buyItems;
    private final List<BuyItems> buyAllItems;


    //private BuyItems[] buyItems;
    //private final ArrayList<BuyItems> buyItemsArrayList;
    //private BuyFragment context;
    //Constructor

    public BuyItemAdapter(Context context, List <BuyItems> buyItems) {
        this.context = context;
        this.buyItems = buyItems;
        buyAllItems = new ArrayList<>(buyItems);
    }


    @androidx.annotation.NonNull
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.townOfSeller.setText(buyItems.get(position).getTownOfSeller());
        holder.wardOfSeller.setText(buyItems.get(position).getWardOfSeller());
        holder.streetOfSeller.setText(buyItems.get(position).getStreetOfSeller());
        holder.phoneNumber.setText(buyItems.get(position).getPhoneNumber());
        holder.numberOfProduct.setText(String.valueOf(buyItems.get(position).getNumberOfProduct()));
        holder.priceOfProduct.setText(String.valueOf(buyItems.get(position).getPriceOfProduct()));
        holder.priceOfProduct.setText(String.format(Locale.US, "%,d",buyItems.get(position).getPriceOfProduct()));
        holder.typeOfProduct.setText(buyItems.get(position).getTypeOfItem());
        holder.extraDescription.setText(buyItems.get(position).getExtraDescription());

        holder.phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + buyItems.get(position).getPhoneNumber()));
                context.startActivity(intent);
            }
        });



           }

    @Override
    public int getItemCount() {
        return buyItems.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View container;
        TextView townOfSeller,wardOfSeller, streetOfSeller, typeOfProduct,phoneNumber,numberOfProduct, priceOfProduct, extraDescription;


        public ViewHolder(View view){
            super(view);
            container = view;
            townOfSeller =view.findViewById(R.id.townOfSeller);
            wardOfSeller = view.findViewById(R.id.wardOfSeller);
            streetOfSeller = view.findViewById(R.id.streetOfSeller);
            phoneNumber = view.findViewById(R.id.phoneNumber);
            typeOfProduct = view.findViewById(R.id.typ);
            numberOfProduct = view.findViewById(R.id.qty);
            priceOfProduct = view.findViewById(R.id.price);
            extraDescription = view.findViewById(R.id.description);
            //priceOfProduct.setText(String.format("%,d", priceOfProduct).replace(',',' '));



        }



    }


    public void setFilter(List<BuyItems> mBuyItems){
        buyItems = new ArrayList<>();
        buyItems.addAll(mBuyItems);
        notifyDataSetChanged();
    }

 }



