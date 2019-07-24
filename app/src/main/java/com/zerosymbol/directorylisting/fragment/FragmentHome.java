package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.adapter.AdapterCategoryList;
import com.zerosymbol.directorylisting.adapter.AdapterFeaturedListing;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.databinding.FragmentHomeBinding;
import com.zerosymbol.directorylisting.models.ModelBase;
import com.zerosymbol.directorylisting.service.ApiServices;
import com.zerosymbol.directorylisting.service.ApiUtils;
import com.zerosymbol.directorylisting.service.RequestFormater;
import com.zerosymbol.directorylisting.support.AppPrefrence;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.FlowOrganizer;
import com.zerosymbol.directorylisting.support.NetworkUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends BaseFragment {

    private FragmentHomeBinding binding;
    private AdapterCategoryList categoryList;
    private AdapterFeaturedListing featuredListing, featuredListing2;
    private ApiServices mAPIService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().setTitle("Home");
        mAPIService = ApiUtils.getAPIService("FragmentHome");

        hitforcat();
        binding.rvFeaturedListingState.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManagerf = new LinearLayoutManager(AppSingle.getInstance().getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvFeaturedListingState.setLayoutManager(layoutManagerf);
        featuredListing = new AdapterFeaturedListing();
        binding.rvFeaturedListingState.setAdapter(featuredListing);
        featuredListing.registerOnClickListener(new AdapterFeaturedListing.IOnClickListener() {
            @Override
            public void onClick() {
                FlowOrganizer.getInstance().add(new FragmentUserDetails(), false);
            }
        });
        binding.rvFeaturedListingIndia.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManagerfi = new LinearLayoutManager(AppSingle.getInstance().getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvFeaturedListingIndia.setLayoutManager(layoutManagerfi);
        featuredListing2 = new AdapterFeaturedListing();
        binding.rvFeaturedListingIndia.setAdapter(featuredListing2);
        featuredListing2.registerOnClickListener(new AdapterFeaturedListing.IOnClickListener() {
            @Override
            public void onClick() {
                FlowOrganizer.getInstance().add(new FragmentUserDetails(), true);
            }
        });
        binding.btnLintNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new FragmentList(), true);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////
        if (AppPrefrence.getInstance().getCategory() != null) {
            binding.rvCat.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(AppSingle.getInstance().getActivity(), 5);
            binding.rvCat.setLayoutManager(layoutManager);
            categoryList = new AdapterCategoryList(AppPrefrence.getInstance().getCategory());
            binding.rvCat.setAdapter(categoryList);
        }
        return binding.getRoot();
    }

    public void hitforcat() {
        if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
            mAPIService.requestwsession(RequestFormater.formatCategoryRequest(), AppPrefrence.getInstance().getInitiator(),
                    AppSingle.getInstance().getAppVersion(), AppPrefrence.getInstance().getsessionId())
                    .enqueue(new Callback<ModelBase>() {
                        @Override
                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                            ModelBase data = response.body();
                            List<String> datum = null;
                            try {
                                if (data == null) {
                                    Log.e("onResponse :: ", "response is null");
                                    Toast.makeText(AppSingle.getInstance().getActivity(), "response is null", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (data.getStatus().contains("error")) {
                                        if (data.getResultDescription() != null)
                                            showResponseDialog("Home", data.getResultDescription(), data.getTID());
                                        else {
                                            showResponseDialog("Home", data.getResultNamespace(),
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                    } else if (data.getStatus().contains("success")) {
                                        datum = new ArrayList<>();
                                        for (int i = 0; i < data.getData().size(); i++) {
                                            datum.add(data.getData().get(i).getName());
                                        }
                                        AppPrefrence.getInstance().setCategory(datum);
                                        binding.rvCat.setHasFixedSize(true);
                                        GridLayoutManager layoutManager = new GridLayoutManager(AppSingle.getInstance().getActivity(), 5);
                                        binding.rvCat.setLayoutManager(layoutManager);
                                        categoryList = new AdapterCategoryList(datum);
                                        binding.rvCat.setAdapter(categoryList);
                                    } else {
                                        if (data.getResultDescription() != null)
                                            showResponseDialog("Home", data.getResultDescription(), data.getTID());
                                        else {
                                            showResponseDialog("Home", data.getResultNamespace(),
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Exception in Login :: ", e.toString());
                                Toast.makeText(AppSingle.getInstance().getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelBase> call, Throwable e) {
                            if (e instanceof SocketTimeoutException)
                                showResponseDialog("Home", SERVER_TIMEOUT);
                            else
                                showResponseDialog("Home", SERVER_GOT_ERROR);
                            Log.e("onError", "yes ");
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }
}
