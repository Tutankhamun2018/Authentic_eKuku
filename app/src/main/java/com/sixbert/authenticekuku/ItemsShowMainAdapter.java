package com.sixbert.authenticekuku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsShowMainAdapter extends RecyclerView.Adapter<ItemsShowMainAdapter.CardViewHolder> {
    private final Context context;
    private final ArrayList<ItemShowMain> itemShowMainArrayList;

    public ItemsShowMainAdapter(Context context, ArrayList<ItemShowMain> itemShowMainArrayList) {
        this.context = context;
        this.itemShowMainArrayList = itemShowMainArrayList;
    }

    @NonNull
    @Override
    public ItemsShowMainAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_main, parent, false);
        return new ItemsShowMainAdapter.CardViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsShowMainAdapter.CardViewHolder holder, int position) {
        ItemShowMain model = itemShowMainArrayList.get(position);
        holder.itemIV.setImageResource(model.getItemImage());
        holder.itemName.setText(model.getItemName());
        holder.sellersNumber.setText(model.getUsersCount());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  static class CardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemIV;
        private final TextView itemName;
        private final TextView sellersNumber;

        public CardViewHolder(@NonNull View itemView){
            super(itemView);
            itemIV=itemView.findViewById(R.id.itemImage);
            itemName= itemView.findViewById(R.id.itemName);
            sellersNumber =itemView.findViewById(R.id.itemQty);
        }
    }
}
