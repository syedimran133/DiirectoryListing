package com.zerosymbol.directorylisting.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.CategoryElementsBinding;
import java.util.List;

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.RecyclerViewHolders> {

    String[] color = {"#FF007F", "#FF0000", "#008000", "#FFFF00", "#7FFF00", "#00FF00", "#008000", "#FFFF00", "#7FFF00", "#00FF00"};
    String[] type = {"ABC", "EFD", "TRD", "WFD", "EKJ", "KJH", "WFD", "EKJ", "KJH", "KJH"};
    List<String> data = null;

    public AdapterCategoryList(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AdapterCategoryList.RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_elements, parent, false);
        AdapterCategoryList.RecyclerViewHolders rcv = new AdapterCategoryList.RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategoryList.RecyclerViewHolders recyclerViewHolders, int i) {

        //for (int j = 0; j < 10; j++) {
            //recyclerViewHolders.binding.alert.setBackgroundColor(Color.parseColor(color[i]));
            recyclerViewHolders.binding.textView.setText(data.get(i));
       // }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CategoryElementsBinding binding;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
          /*  BeneficiaryDetail data = itemList.get(getAdapterPosition());
            if (ionItemSelect != null)
                ionItemSelect.onSelected(getAdapterPosition(), itemList.get(getAdapterPosition()));*/
        }

    }
}
