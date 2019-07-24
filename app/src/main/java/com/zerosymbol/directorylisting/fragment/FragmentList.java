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
import com.zerosymbol.directorylisting.databinding.FragmentListBinding;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.FlowOrganizer;

public class FragmentList extends BaseFragment {

    private FragmentListBinding binding;
    private AdapterMainList mainListAdpt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().setTitle("");
        binding.rvList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(AppSingle.getInstance().getActivity());
        binding.rvList.setLayoutManager(layoutManager);
        mainListAdpt = new AdapterMainList();
        binding.rvList.setAdapter(mainListAdpt);
        mainListAdpt.registerOnClickListener(new AdapterMainList.IOnClickListener() {
            @Override
            public void onClick() {
                FlowOrganizer.getInstance().add(new FragmentUserDetails(), true);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
