package com.zerosymbol.directorylisting.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.FeaturedListingElementsBinding;

public class AdapterFeaturedListing extends RecyclerView.Adapter<AdapterFeaturedListing.RecyclerViewHolders> {

    private AdapterFeaturedListing.IOnClickListener listener;

    public AdapterFeaturedListing() {

    }

    @NonNull
    @Override
    public AdapterFeaturedListing.RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_listing_elements, parent, false);
        AdapterFeaturedListing.RecyclerViewHolders rcv = new AdapterFeaturedListing.RecyclerViewHolders(layoutView);
        return rcv;
    }

    public void registerOnClickListener(AdapterFeaturedListing.IOnClickListener listener) {

        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeaturedListing.RecyclerViewHolders holder, int i) {
        if (i % 2 == 0) {
            holder.binding.tvCat.setText("Silver");
            holder.binding.tvCat.setBackgroundResource(R.color.silver);
        } else {
            holder.binding.tvCat.setText("GOLD");
            holder.binding.tvCat.setBackgroundResource(R.color.gold);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {

        public FeaturedListingElementsBinding binding;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onClick();
                }
            });
        }


    }

    public interface IOnClickListener {
        void onClick();
    }
}
