package com.sixbert.authenticekuku;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sixbert.authenticekuku.Filter;

import java.util.HashMap;
import java.util.Objects;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private final HashMap<Integer, Filter> filters;
    private final RecyclerView filterRecyclerView;
    private  int selectedPosition = 0;

    public FilterAdapter(HashMap<Integer, Filter> filters, RecyclerView filterRecyclerView){
        this.filters = filters;
        this.filterRecyclerView = filterRecyclerView;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup viewGroup, int position){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_item, viewGroup, false);
        return new FilterViewHolder(view);
    }

    public  void onBindViewHolder(final FilterViewHolder filterViewHolder, int position){
        filterViewHolder.container.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                filterRecyclerView.setAdapter(new FilterValuesAdapter(position));
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

        filterRecyclerView.setAdapter(new FilterValuesAdapter(selectedPosition));
        filterViewHolder.container.setBackgroundColor(selectedPosition == position ? Color.WHITE : Color.TRANSPARENT);
        filterViewHolder.title.setText(Objects.requireNonNull(filters.get(position)).getName());
        }

        public  int getItemCount(){
        return filters.size();
        }

        public static class FilterViewHolder extends RecyclerView.ViewHolder{
        final View container;
        final TextView title;

        FilterViewHolder (View view){
            super(view);
            container = view;
            title = view.findViewById(R.id.title);
        }
        }

}
