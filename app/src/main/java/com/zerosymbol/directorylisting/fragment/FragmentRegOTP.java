package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.FragmentRegOtpBinding;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.FlowOrganizer;

public class FragmentRegOTP extends BaseFragment {

    private FragmentRegOtpBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reg_otp, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(false);
        //AppSingle.getInstance().getActivity().setTitle("Register");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.inputOtp.getText().toString().equalsIgnoreCase("")) {
                    FlowOrganizer.getInstance().add(new FragmentLogin(), false);
                } else {
                    Toast.makeText(AppSingle.getInstance().getActivity(),"OTP field is empty",Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.linkSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new FragmentHome(), false);
            }
        });
    }
}
