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
import com.zerosymbol.directorylisting.databinding.FragmentListBinding;
import com.zerosymbol.directorylisting.support.AppPrefrence;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.FlowOrganizer;
import com.zerosymbol.directorylisting.support.SupportUtils;

public class FragmentLunagang extends BaseFragment {

    private FragmentListBinding binding;
    private AdapterLungange mainListAdpt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(false);
        AppSingle.getInstance().getActivity().setTitle("");

        binding.rvList.setHasFixedSize(true);

        // use a linear layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(AppSingle.getInstance().getActivity(), 2);
        binding.rvList.setLayoutManager(layoutManager);

        binding.latlog.setVisibility(View.GONE);

        mainListAdpt = new AdapterLungange(SupportUtils.getlung());
        mainListAdpt.registerOnClickListener(new AdapterLungange.IOnClickListener() {
            @Override
            public void onClick(String data, int position) {
                AppPrefrence.getInstance().setiSLunagang(true);
                AppPrefrence.getInstance().setLunagang(data);
                if (AppPrefrence.getInstance().isActivityExecuted()) {
                    FlowOrganizer.getInstance().add(new FragmentHome(), true);
                } else {
                    FlowOrganizer.getInstance().add(new FragmentLogin(), false);
                }
            }
        });
        binding.rvList.setAdapter(mainListAdpt);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
