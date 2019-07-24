package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.adapter.AdapterMainList;
import com.zerosymbol.directorylisting.adapter.AdapterMyListing;
import com.zerosymbol.directorylisting.databinding.FragmentFavouritesBinding;
import com.zerosymbol.directorylisting.databinding.FragmentOtpBinding;
import com.zerosymbol.directorylisting.support.AppSingle;

public class FragmentFavourites extends BaseFragment {

    private FragmentFavouritesBinding binding;
    private AdapterMyListing mainListAdpt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().setTitle("Favourites");

        binding.rvList.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(AppSingle.getInstance().getActivity());
        binding.rvList.setLayoutManager(layoutManager);

        mainListAdpt = new AdapterMyListing();
        binding.rvList.setAdapter(mainListAdpt);
        return binding.getRoot();
    }
}
