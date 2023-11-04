package com.sixbert.authenticekuku;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import io.reactivex.rxjava3.annotations.NonNull;


public class BuyItemAdapter extends RecyclerView.Adapter<BuyItemAdapter.ViewHolder>
 {


    public final Context context;
    private List<BuyItems> buyItems;


     public BuyItemAdapter(Context context, List <BuyItems> buyItems) {
        this.context = context;
        this.buyItems = buyItems;
        List<BuyItems> buyAllItems = new ArrayList<>(buyItems);
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
        Glide.with(holder.itemView.getContext()).load(buyItems.get(position).getImageUrl()).into(holder.imageView);

        holder.phoneNumber.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + buyItems.get(position).getPhoneNumber()));
            context.startActivity(intent);
        });

        }

    @Override
    public int getItemCount() {
        return buyItems.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View container;
        ImageView imageView;
        final TextView townOfSeller;
        final TextView wardOfSeller;
        final TextView streetOfSeller;
        final TextView typeOfProduct;
        final TextView phoneNumber;
        final TextView numberOfProduct;
        final TextView priceOfProduct;
        final TextView extraDescription;


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
            imageView = view.findViewById(R.id.imageUrl);

        }

    }

    public void setFilter(List<BuyItems> mBuyItems){
        buyItems = new ArrayList<>();
        buyItems.addAll(mBuyItems);
        notifyDataSetChanged();
    }

 }



