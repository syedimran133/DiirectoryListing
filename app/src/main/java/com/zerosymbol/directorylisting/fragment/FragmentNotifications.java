package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.FragmentNotificationsBinding;
import com.zerosymbol.directorylisting.support.AppSingle;

public class FragmentNotifications extends BaseFragment {

    private FragmentNotificationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().setTitle("Notifications");
        return binding.getRoot();
    }
}
