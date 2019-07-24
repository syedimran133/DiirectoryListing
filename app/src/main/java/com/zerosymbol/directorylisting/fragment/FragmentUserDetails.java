package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.adapter.AdapterLungange;
import com.zerosymbol.directorylisting.adapter.AdapterStringRV;
import com.zerosymbol.directorylisting.databinding.FragmentUserDetailsBinding;
import com.zerosymbol.directorylisting.support.AppPrefrence;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.FlowOrganizer;
import com.zerosymbol.directorylisting.support.SupportUtils;

public class FragmentUserDetails extends BaseFragment {

    private FragmentUserDetailsBinding binding;
    private AdapterStringRV mainListAdpt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().HideIcon();
        AppSingle.getInstance().getActivity().setTitle("User Detail");
        AppSingle.getInstance().getActivity().setTitleWSub("Rajesh Kumar","ABC");

        // use a linear layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(AppSingle.getInstance().getActivity(), 3);
        binding.rvCat.setLayoutManager(layoutManager);

        mainListAdpt = new AdapterStringRV(SupportUtils.getusercat());
        mainListAdpt.registerOnClickListener(new AdapterStringRV.IOnClickListener() {
            @Override
            public void onClick(String data, int position) {

            }
        });
        binding.rvCat.setAdapter(mainListAdpt);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
