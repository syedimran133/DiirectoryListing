package com.zerosymbol.directorylisting.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.databinding.FragmentLoginBinding;
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

public class FragmentLogin extends BaseFragment {

    private FragmentLoginBinding binding;
    private ApiServices mAPIService;
    private String password, number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(false);
        AppSingle.getInstance().getActivity().setTitle("Login");
        mAPIService = ApiUtils.getAPIService("FragmentLogin");
        return binding.getRoot();
    }

    private void switchLoginWOtp(String state) {
        if (state.equalsIgnoreCase("Login with OTP")) {
            binding.inputPassword.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.GONE);
            binding.btnLoginwotp.setText("Generate OTP");
        } else if (state.equalsIgnoreCase("Generate OTP")) {
            binding.inputPassword.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnLoginwotp.setText("Login with OTP");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLoginwotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding.lablePassword.setHelperText("OTP");
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.inputNumber.getText().toString().equalsIgnoreCase("")) {
                    if (!binding.inputPassword.getText().toString().equalsIgnoreCase("")) {
                        callasyc();
                        //FlowOrganizer.getInstance().add(new FragmentOTP(), false);
                    } else {
                        Toast.makeText(AppSingle.getInstance().getActivity(), "Password field is empty", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AppSingle.getInstance().getActivity(), "Email field is empty", Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.btnLoginwotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.btnLoginwotp.getText().toString().equalsIgnoreCase("Login with OTP")) {
                    switchLoginWOtp("Login with OTP");
                } else if (binding.btnLoginwotp.getText().toString().equalsIgnoreCase("Generate OTP")) {
                    callasycLWO();
                }
            }
        });
        binding.linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new FragmentRegister(), true);
            }
        });
        binding.linkSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FlowOrganizer.getInstance().add(new FragmentHome(), false);
                FlowOrganizer.getInstance().add(new FragmentAboutus(), false);
            }
        });
    }

    public void callasyc() {
        password = binding.inputPassword.getText().toString();
        number = binding.inputNumber.getText().toString();
        if (AppValidate.isValidMobileNo(number)) {
            if (AppValidate.isSixDigit(password)) {
                hitforlogin();
            } else {
                showToast("Password is invalid");
            }
        } else {
            showToast("Please enter valid mobile number");
        }
    }

    public void callasycLWO() {
        number = binding.inputNumber.getText().toString();
        if (AppValidate.isValidMobileNo(number)) {
            hitforge_login_otp();
        } else {
            showToast("Please enter valid mobile number");
        }
    }

    public void hitforlogin() {
        if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
            setProgressDialog("Authentication is in progress..");
            mAPIService.request(RequestFormater.formatloginRequest(AppPrefrence.getInstance().getAppKey(), password), number,
                    AppSingle.getInstance().getAppVersion())
                    .enqueue(new Callback<ModelBase>() {
                        @Override
                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                            dismissProgressDialog();
                            ModelBase data = response.body();
                            try {
                                if (data == null) {
                                    Log.e("onResponse :: ", "response is null");
                                } else {
                                    if (data.getStatus().contains("error")) {
                                        if (data.getResultCode().equalsIgnoreCase("1001") || data.getResultCode().equalsIgnoreCase("6")) {
                                            FragmentOTP fragment = new FragmentOTP();
                                            fragment.setData(data.getTID(), number, password, 0);
                                            FlowOrganizer.getInstance().add(fragment, false);
                                        } else if (data.getResultDescription() == null || data.getResultDescription().trim().equalsIgnoreCase("NA") || data.getResultDescription().trim().equalsIgnoreCase("")) {
                                            showResponseDialog("Login", data.getResultNamespace(),
                                                    String.valueOf(data.getTID()));
                                        } else {
                                            if (data.getResultDescription() != null)
                                                showResponseDialog("Login", data.getResultDescription(), data.getTID());
                                            else {
                                                showResponseDialog("Login", data.getResultNamespace(),
                                                        data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                            }
                                        }
                                    } else if (data.getStatus().contains("success")) {
                                        AppPrefrence.getInstance().setAppKey(data.getAppKey());
                                        AppPrefrence.getInstance().setTypeId(data.getTypeId());
                                        AppPrefrence.getInstance().setInitiator(number);
                                        AppPrefrence.getInstance().setName(data.getName());
                                        AppPrefrence.getInstance().setRating(data.getRating());
                                        AppPrefrence.getInstance().setsessionId(data.getSessionId());
                                        AppPrefrence.getInstance().setLocation(data.getLocation());
                                        AppPrefrence.getInstance().setEmail(data.getEmail());
                                        AppPrefrence.getInstance().setPin(password);
                                        AppSingle.getInstance().setSuccessLogin(true);
                                        AppPrefrence.getInstance().setActivityExecuted(true);
                                        AppSingle.getInstance().setUserName(data.getName());
                                        AppSingle.getInstance().setUserEmail(data.getEmail());
                                        FlowOrganizer.getInstance().add(new FragmentHome());
                                    } else {
                                        if (data.getResultDescription() != null) {
                                            showResponseDialog("Register", data.getResultDescription(), data.getTID());
                                        } else {
                                            showResponseDialog("Register", data.getResultNamespace(),
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Exception in Login :: ", e.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelBase> call, Throwable e) {
                            dismissProgressDialog();
                            if (e instanceof SocketTimeoutException)
                                showResponseDialog("Login", SERVER_TIMEOUT);
                            else
                                showResponseDialog("Login", SERVER_GOT_ERROR);
                            Log.e("onError", "yes ");
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }

    public void hitforge_login_otp() {
        if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
            setProgressDialog("Generating OTP..");
            mAPIService.request(RequestFormater.formatLoginWOtpRequest(number), number,
                    AppSingle.getInstance().getAppVersion())
                    .enqueue(new Callback<ModelBase>() {
                        @Override
                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                            dismissProgressDialog();
                            ModelBase data = response.body();
                            try {
                                if (data == null) {
                                    Log.e("onResponse :: ", "response is null");
                                } else {
                                    if (data.getStatus().contains("error")) {
                                        if (data.getResultDescription() != null)
                                            showResponseDialog("Login", data.getResultDescription(), data.getTID());
                                        else {
                                            showResponseDialog("Login", data.getResultNamespace(),
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                    } else if (data.getStatus().contains("success")) {
                                        FragmentOTP fragment = new FragmentOTP();
                                        fragment.setData(data.getTID(), number, "NA", 0);
                                        FlowOrganizer.getInstance().add(fragment, false);
                                    } else {
                                        if (data.getResultDescription() != null)
                                            showResponseDialog("Login", data.getResultDescription(), data.getTID());
                                        else {
                                            showResponseDialog("Login", data.getResultNamespace(),
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Exception in Login :: ", e.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelBase> call, Throwable e) {
                            dismissProgressDialog();
                            if (e instanceof SocketTimeoutException)
                                showResponseDialog("Login", SERVER_TIMEOUT);
                            else
                                showResponseDialog("Login", SERVER_GOT_ERROR);
                            Log.e("onError", "yes ");
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }
}
