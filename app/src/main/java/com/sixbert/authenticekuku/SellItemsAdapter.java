package com.sixbert.authenticekuku;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SellItemsAdapter extends FirestoreRecyclerAdapter  <BuyItems, SellItemsAdapter.SellViewHolder> {

    final Context context;



    public SellItemsAdapter(Context context, @NonNull FirestoreRecyclerOptions<BuyItems> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final SellItemsAdapter.SellViewHolder holder, int i, @NonNull BuyItems model) {
        holder.townOfSeller.setText(model.getTownOfSeller());
        holder.wardOfSeller.setText(model.getWardOfSeller());
        holder.streetOfSeller.setText(model.getStreetOfSeller());
        holder.phoneNumber.setText(model.getPhoneNumber());
        holder.numberOfProduct.setText(String.valueOf(model.getNumberOfProduct()));
        holder.priceOfProduct.setText(String.valueOf(model.getPriceOfProduct()));
        holder.typeOfProduct.setText(model.getTypeOfItem());
        holder.extraDescription.setText(model.getExtraDescription());

        final String documentId = getSnapshots().getSnapshot(i).getId();

        Glide.with(holder.itemView.getContext()).load(model.getImageUrl()).into(holder.imageView);

        holder.edit.setOnClickListener(v -> {
            Intent i1 = new Intent(context, EditKukuActivity.class);
            i1.putExtra("documentId", documentId);
            context.startActivity(i1);
        });

    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        //super.onError(e);
    }


    @NonNull
    @Override
    public SellItemsAdapter.SellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sell, parent, false);

        return new SellItemsAdapter.SellViewHolder(view);
    }


    public static class SellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View container;
        final TextView townOfSeller;
        final TextView wardOfSeller;
        final TextView streetOfSeller;
        final TextView typeOfProduct;
        final TextView phoneNumber;
        final TextView numberOfProduct;
        final TextView priceOfProduct;
        final TextView extraDescription;
        final Button edit;
        final Button delete;
        final ImageView imageView;

        public SellViewHolder(View view) {
            super(view);
            container = view;
            townOfSeller = view.findViewById(R.id.townOfSeller);
            wardOfSeller =view.findViewById(R.id.wardOfSeller);
            streetOfSeller = view.findViewById(R.id.streetOfSeller);
            phoneNumber = view.findViewById(R.id.phoneNumber);
            typeOfProduct = view.findViewById(R.id.typ);
            numberOfProduct = view.findViewById(R.id.qty);
            priceOfProduct = view.findViewById(R.id.price);
            extraDescription = view.findViewById(R.id.xtraDescription);
            edit = view.findViewById(R.id.edit);
            delete = view.findViewById(R.id.delete);
            imageView = view.findViewById(R.id.imageUrl);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
