package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.FragmentContactusBinding;
import com.zerosymbol.directorylisting.databinding.FragmentOtpBinding;
import com.zerosymbol.directorylisting.support.AppSingle;

public class FragmentContactUs extends BaseFragment {

    private FragmentContactusBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contactus, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().setTitle("Contact Us");
        return binding.getRoot();
    }
}
