package com.zerosymbol.directorylisting.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.LayoutLungBinding;
import java.util.List;

public class AdapterLungange extends RecyclerView.Adapter<AdapterLungange.CustomHolder> {

    private List<String> listData;
    private AdapterLungange.IOnClickListener listener;

    public AdapterLungange(List<String> listData) {
        this.listData = listData;
    }

    protected class CustomHolder extends RecyclerView.ViewHolder {
        public LayoutLungBinding binding;

        public CustomHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);

            binding.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onClick(listData.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
    public void registerOnClickListener(AdapterLungange.IOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_lung, viewGroup, false);
        return new CustomHolder(view);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int i) {
        holder.binding.tvText.setText(listData.get(i));
    }

    public interface IOnClickListener {
        void onClick(String data, int position);
    }
}
