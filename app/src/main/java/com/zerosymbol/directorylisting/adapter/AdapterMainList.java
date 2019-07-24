package com.zerosymbol.directorylisting.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.ElementMainListBinding;
import com.zerosymbol.directorylisting.databinding.ElementTextviewBinding;
import com.zerosymbol.directorylisting.databinding.MainListBinding;

import java.util.List;

public class AdapterMainList extends RecyclerView.Adapter<AdapterMainList.RecyclerViewHolders> {


    private List<String> listData;
    private AdapterMainList.IOnClickListener listener;

    public AdapterMainList() {
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_main_list, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    public void registerOnClickListener(AdapterMainList.IOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders recyclerViewHolders, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder{

        public ElementMainListBinding binding;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.listRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
