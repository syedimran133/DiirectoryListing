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
import com.zerosymbol.directorylisting.adapter.AdapterCategoryList;
import com.zerosymbol.directorylisting.adapter.AdapterFeaturedListing;
import com.zerosymbol.directorylisting.databinding.FragmentPaymentBillingBinding;
import com.zerosymbol.directorylisting.support.AppSingle;

public class FragmentPaymentsBilling extends BaseFragment {

    private FragmentPaymentBillingBinding binding;
    private AdapterCategoryList mainListAdpt;
    private AdapterFeaturedListing featuredListing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_billing, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().setTitle("Payment & Billing");


        return binding.getRoot();
    }
}
