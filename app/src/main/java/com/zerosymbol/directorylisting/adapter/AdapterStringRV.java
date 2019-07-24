package com.zerosymbol.directorylisting.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.ElementTextviewBinding;
import com.zerosymbol.directorylisting.databinding.LayoutLungBinding;

import java.util.List;

public class AdapterStringRV extends RecyclerView.Adapter<AdapterStringRV.CustomHolder> {

    private List<String> listData;
    private AdapterStringRV.IOnClickListener listener;
    private int isSelected = 0;

    public AdapterStringRV(List<String> listData) {
        this.listData = listData;
    }

    protected class CustomHolder extends RecyclerView.ViewHolder {
        public ElementTextviewBinding binding;

        public CustomHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);

            binding.tvTitel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(listData.get(getAdapterPosition()), getAdapterPosition());
                        isSelected = getAdapterPosition();
                    }
                }
            });
        }
    }

    public void registerOnClickListener(AdapterStringRV.IOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterStringRV.CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_textview, viewGroup, false);
        return new AdapterStringRV.CustomHolder(view);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStringRV.CustomHolder holder, int i) {
        holder.binding.tvTitel.setText(listData.get(i));
        if (i == isSelected) {
            holder.binding.tvTitel.setBackgroundResource(R.drawable.textview_background_selected);
            holder.binding.tvTitel.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.binding.tvTitel.setBackgroundResource(R.drawable.textview_background);
            holder.binding.tvTitel.setTextColor(Color.parseColor("#009D74"));
        }
    }

    public interface IOnClickListener {
        void onClick(String data, int position);
    }
}
