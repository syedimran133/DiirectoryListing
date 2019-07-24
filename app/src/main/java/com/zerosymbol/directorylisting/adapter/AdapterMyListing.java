package com.zerosymbol.directorylisting.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.MyListingElementBinding;

public class AdapterMyListing extends RecyclerView.Adapter<AdapterMyListing.RecyclerViewHolders> {


    public AdapterMyListing() {
    }

    @NonNull
    @Override
    public AdapterMyListing.RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_listing_element,  parent, false);
        AdapterMyListing.RecyclerViewHolders rcv = new AdapterMyListing.RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders recyclerViewHolders, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MyListingElementBinding binding;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }

    }
}
