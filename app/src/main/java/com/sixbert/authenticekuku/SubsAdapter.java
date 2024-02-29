package com.sixbert.authenticekuku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubsAdapter extends RecyclerView.Adapter<SubsAdapter.ViewHolder> {

    Context context;
    ArrayList<MultiSubs> subsList;

    private OnItemClickListener subsListener;

    public  SubsAdapter (Context context, ArrayList<MultiSubs> subsList){
        this.context = context;
        this. subsList = subsList;
    }

    public  interface OnItemClickListener {

        void  onItemClick (int position);

    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        subsListener = listener;
    }
    @NonNull
    @Override
    public SubsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(context).inflate(R.layout.subscriptionlist, parent, false);
        SubsAdapter.ViewHolder viewHolder = new ViewHolder(itemView, subsListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubsAdapter.ViewHolder holder, int position) {
        //holder.name.setText(subsList.get(position).planName);
        holder.price.setText(subsList.get(position).planPrice);

    }

    @Override
    public int getItemCount() {
        return subsList.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        TextView price;//name, ;

        public  ViewHolder (View itemView, final  OnItemClickListener listener){
            super(itemView);
            //name = itemView.findViewById(R.id.subsType);
            price = itemView.findViewById(R.id.subsPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener !=null){
                        int position = getAbsoluteAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }


                    }
                }
            });
        }
    }
}
