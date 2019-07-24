package com.zerosymbol.directorylisting.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.ListItemDialogBinding;
import com.zerosymbol.directorylisting.models.UserType;

import java.util.List;

public class AdapterDialogListUserTypeItem extends RecyclerView.Adapter<AdapterDialogListUserTypeItem.RecyclerViewHolders>  {

    private List<UserType> itemList;
    private IonItemSelect ionItemSelect;
    private int selectedPos = -1;

    public AdapterDialogListUserTypeItem(List<UserType> itemList, int selectedPos) {
        this.itemList = itemList;
        this.selectedPos = selectedPos;
    }

    public void registerOnItemClickListener(IonItemSelect ionItemSelect) {
        this.ionItemSelect = ionItemSelect;
    }
    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ListItemDialogBinding binding;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ionItemSelect != null)
                ionItemSelect.onItemSelect(getAdapterPosition());
        }
    }


    @Override
    public AdapterDialogListUserTypeItem.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dialog, null);
        AdapterDialogListUserTypeItem.RecyclerViewHolders rcv = new AdapterDialogListUserTypeItem.RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(AdapterDialogListUserTypeItem.RecyclerViewHolders holder, int position) {
        holder.binding.txtViewTitle.setText(itemList.get(position).getName());
        if (selectedPos == position) {
            holder.binding.txtViewTitle.setSelected(true);
            holder.binding.chkBox.setChecked(true);
        } else {
            holder.binding.txtViewTitle.setSelected(false);
            holder.binding.chkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
    public interface IonItemSelect {
        void onItemSelect(int position);
    }
}
