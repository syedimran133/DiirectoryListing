package com.zerosymbol.directorylisting.fragment;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.adapter.AdapterDialogListItem2;
import com.zerosymbol.directorylisting.adapter.AdapterDialogListUserTypeItem;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.databinding.CustomPopupBinding;
import com.zerosymbol.directorylisting.databinding.DialogListBinding;
import com.zerosymbol.directorylisting.listner.IDialogEventListener;
import com.zerosymbol.directorylisting.listner.IDialogListClickListener;
import com.zerosymbol.directorylisting.listner.IDialogUserTypeListener;
import com.zerosymbol.directorylisting.models.UserType;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.JsonUtils;
import java.util.List;

public class BaseFragment extends Fragment implements AppConstants.IMessages, AppConstants.IRequestResponseUtils  {

    public void showResponseDialog(final String title, final String message) {
        dismissProgressDialog();
        AppSingle.getInstance().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustomPopupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(AppSingle.getInstance().getActivity().getApplicationContext()), R.layout.custom_popup, null, false);
                final Dialog dialog = new Dialog(AppSingle.getInstance().getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(binding.getRoot());
                dialog.setTitle(title);
                dialog.setCanceledOnTouchOutside(false);
                TextView dialogOk = binding.btnOk;
                TextView dialogCancal = binding.btnCancal;
                TextView txt = binding.txt;
                txt.setText(message);
                dialogCancal.setVisibility(View.GONE);
                dialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    public void showResponseDialog(final String title, final String message, final String TranId) {
        AppSingle.getInstance().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustomPopupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(AppSingle.getInstance().getActivity().getApplicationContext()), R.layout.custom_popup, null, false);
                final Dialog dialog = new Dialog(AppSingle.getInstance().getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(binding.getRoot());
                dialog.setTitle(title);
                dialog.setCanceledOnTouchOutside(false);
                TextView dialogOk = binding.btnOk;
                TextView dialogCancal = binding.btnCancal;
                TextView txt = binding.txt;
                txt.setText(message + ",\n Transaction ID : " + TranId);
                dialogCancal.setVisibility(View.GONE);
                dialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    public void setProgressDialog(String requestType) {
        AppSingle.getInstance().getActivity().setProgressDialog(requestType);
    }

    public void dismissProgressDialog() {
        AppSingle.getInstance().getActivity().dismissProgressDialog();
    }
    public void showToast(String message) {
        Toast.makeText(AppSingle.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
    protected void openListDialog2(String title, int selectedPos, final List<String> listData, final IDialogListClickListener listener) {
        DialogListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(AppSingle.getInstance().getActivity().getApplicationContext()), R.layout.dialog_list, null, false);
        final Dialog dialog = new Dialog(AppSingle.getInstance().getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
       /* Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        int margin = getResources().getDimensionPixelSize(R.dimen._15dp);
        lp.x = margin;
        lp.y = margin;
        window.setAttributes(lp);*/
        binding.txtViewTitle.setText(title);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayoutManager lLayout = new LinearLayoutManager(AppSingle.getInstance().getActivity());
        RecyclerView rView = binding.rvList;
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        AdapterDialogListItem2 adapter = new AdapterDialogListItem2(listData, selectedPos);
        rView.setAdapter(adapter);
        adapter.registerOnItemClickListener(new AdapterDialogListItem2.IonItemSelect() {
            @Override
            public void onItemSelect(int position) {
                if (listener != null)
                    listener.onClick(listData.get(position), position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    protected void openListDialogUserType(String title, int selectedPos, final List<UserType> listData, final IDialogUserTypeListener listener) {
        DialogListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(AppSingle.getInstance().getActivity().getApplicationContext()), R.layout.dialog_list, null, false);
        final Dialog dialog = new Dialog(AppSingle.getInstance().getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
       /* Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        int margin = getResources().getDimensionPixelSize(R.dimen._15dp);
        lp.x = margin;
        lp.y = margin;
        window.setAttributes(lp);*/
        binding.txtViewTitle.setText(title);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayoutManager lLayout = new LinearLayoutManager(AppSingle.getInstance().getActivity());
        RecyclerView rView = binding.rvList;
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        AdapterDialogListUserTypeItem adapter = new AdapterDialogListUserTypeItem(listData, selectedPos);
        rView.setAdapter(adapter);
        adapter.registerOnItemClickListener(new AdapterDialogListUserTypeItem.IonItemSelect() {
            @Override
            public void onItemSelect(int position) {
                if (listener != null)
                    listener.onClick(listData.get(position), position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void showResponseDialog(final String title, final String nameSpace,
                                   final String code, final String TranId, final String desc,
                                   final IDialogEventListener listener) {
        AppSingle.getInstance().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustomPopupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(AppSingle.getInstance().getActivity().getApplicationContext()), R.layout.custom_popup, null, false);
                final Dialog dialog = new Dialog(AppSingle.getInstance().getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(binding.getRoot());
                dialog.setTitle(title);
                dialog.setCanceledOnTouchOutside(false);
                TextView dialogOk = binding.btnOk;
                TextView dialogCancal = binding.btnCancal;
                TextView txt = binding.txt;
                String msg = JsonUtils.getMessage(code, nameSpace, desc);
                txt.setText(msg + ".\n Transaction ID : " + TranId);
                dialogCancal.setVisibility(View.GONE);
                dialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null)
                            listener.onOk();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
