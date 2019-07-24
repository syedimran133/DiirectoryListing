package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.databinding.FragmentOtpBinding;
import com.zerosymbol.directorylisting.models.ModelBase;
import com.zerosymbol.directorylisting.service.ApiServices;
import com.zerosymbol.directorylisting.service.ApiUtils;
import com.zerosymbol.directorylisting.service.RequestFormater;
import com.zerosymbol.directorylisting.support.AppPrefrence;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.AppValidate;
import com.zerosymbol.directorylisting.support.FlowOrganizer;
import com.zerosymbol.directorylisting.support.NetworkUtil;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOTP extends BaseFragment {

    private FragmentOtpBinding binding;
    private String transTID, otp, number, pin;
    private int type = 0;
    private ApiServices mAPIService;

    public void setData(String transTID, String number, String pin, int type) {
        this.transTID = transTID;
        this.number = number;
        this.pin = pin;
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(false);
        AppSingle.getInstance().getActivity().setTitle("Verify OTP");
        mAPIService = ApiUtils.getAPIService("FragmentOTP");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startCounDown();
        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.inputOtp.getText().toString().equalsIgnoreCase("")) {
                    callAsync();
                } else {

                    Toast.makeText(AppSingle.getInstance().getActivity(), "OTP field is empty", Toast.LENGTH_LONG).show();
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

    private void callAsync() {
        EditText edt_txt_otp = binding.inputOtp;
        otp = edt_txt_otp.getText().toString();
        if (AppValidate.isSixDigit(otp))
            hitforloginwotp();
        else
            showToast(VALID_6_DIGIT);
    }

    private void startCounDown() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                //binding.edtTxtOtp.setText("");
                binding.txtViewReSendOtp.setVisibility(View.GONE);
                binding.txtViewTimer.setVisibility(View.VISIBLE);
                binding.txtViewTimer.setText("Re-Send OTP in " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                binding.txtViewTimer.setVisibility(View.GONE);
                binding.txtViewReSendOtp.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void hitforloginwotp() {
        if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
            setProgressDialog("Verifying otp in progress..");
            mAPIService.request(RequestFormater.formatloginWotpRequest(otp, transTID), number,
                    AppSingle.getInstance().getAppVersion())
                    .enqueue(new Callback<ModelBase>() {
                        @Override
                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                            dismissProgressDialog();
                            ModelBase data = response.body();
                            try {
                                if (data == null) {
                                    Log.e("onResponse :: ", "response is null");
                                    AppPrefrence.getInstance().setActivityExecuted(false);
                                } else {
                                    if (data.getStatus().contains("error")) {
                                        AppPrefrence.getInstance().setActivityExecuted(false);
                                        showResponseDialog("Login", data.getResultNamespace(), String.valueOf(data.getTID()));
                                    } else if (data.getStatus().contains("success")) {
                                        AppPrefrence.getInstance().setActivityExecuted(true);
                                        AppPrefrence.getInstance().setAppKey(data.getAppKey());
                                        AppPrefrence.getInstance().setTypeId(data.getTypeId());
                                        AppPrefrence.getInstance().setInitiator(number);
                                        AppPrefrence.getInstance().setName(data.getName());
                                        AppPrefrence.getInstance().setRating(data.getRating());
                                        AppPrefrence.getInstance().setsessionId(data.getSessionId());
                                        AppPrefrence.getInstance().setLocation(data.getLocation());
                                        AppPrefrence.getInstance().setEmail(data.getEmail());
                                        AppPrefrence.getInstance().setPin(pin);
                                        AppSingle.getInstance().setSuccessLogin(true);
                                        AppSingle.getInstance().setUserName(data.getName());
                                        AppSingle.getInstance().setUserEmail(data.getEmail());
                                        FlowOrganizer.getInstance().add(new FragmentHome());
                                    } else {
                                        AppPrefrence.getInstance().setActivityExecuted(false);
                                        showResponseDialog("Login", data.getResultNamespace(), String.valueOf(data.getTID()));
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Exception in Login :: ", e.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelBase> call, Throwable e) {
                            dismissProgressDialog();
                            AppPrefrence.getInstance().setActivityExecuted(false);
                            if (e instanceof SocketTimeoutException)
                                showResponseDialog("Other Bill Verify", SERVER_TIMEOUT);
                            else
                                showResponseDialog("Other Bill Verify", SERVER_GOT_ERROR);
                            Log.e("onError", "yes ");
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }
}
