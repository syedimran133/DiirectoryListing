package com.zerosymbol.directorylisting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.fragment.BaseFragment;
import com.zerosymbol.directorylisting.fragment.FragmentHome;
import com.zerosymbol.directorylisting.fragment.FragmentOTP;
import com.zerosymbol.directorylisting.models.ModelBase;
import com.zerosymbol.directorylisting.service.ApiServices;
import com.zerosymbol.directorylisting.service.ApiUtils;
import com.zerosymbol.directorylisting.service.RequestFormater;
import com.zerosymbol.directorylisting.support.AppPrefrence;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.FlowOrganizer;
import com.zerosymbol.directorylisting.support.NetworkUtil;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {

    private static String kk = Splash.class.getName(), sesionID = "";
    private static long SLEEP_TIME = 3;
    private Context mContext;
    private TextView tvversion;
    private ApiServices mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            setContentView(R.layout.activity_splash);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
            AppPrefrence.getInstance().initPrefrence(this);
            mContext = this;
            mAPIService = ApiUtils.getAPIService("Splash");
            if (AppPrefrence.getInstance().isActivityExecuted())
                if (!AppPrefrence.getInstance().getPin().equalsIgnoreCase("NA"))
                    hitService();
                else {
                    IntentLauncher launcher = new IntentLauncher();
                    launcher.start();
                } else {
                IntentLauncher launcher = new IntentLauncher();
                launcher.start();
            }
        } catch (Exception e) {
        }
    }

    public void hitService() {
        if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
            //final BaseFragment baseFragment=new BaseFragment();
            //baseFragment.setProgressDialog("Authentication is in progress..");
            mAPIService.request(RequestFormater.formatloginRequest(AppPrefrence.getInstance().getAppKey(), AppPrefrence.getInstance().getPin()), AppPrefrence.getInstance().getInitiator(),
                    AppSingle.getInstance().getAppVersion())
                    .enqueue(new Callback<ModelBase>() {
                        @Override
                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {

                            // baseFragment.dismissProgressDialog();
                            ModelBase data = response.body();
                            try {
                                if (data == null) {
                                    Log.e("onResponse :: ", "response is null");
                                } else {
                                    if (data.getStatus().contains("error")) {
                                        if (data.getResultCode().equalsIgnoreCase("1001") || data.getResultCode().equalsIgnoreCase("6")) {
                                            /*FragmentOTP fragment = new FragmentOTP();
                                            fragment.setData(data.getTID(), AppPrefrence.getInstance().getInitiator(), AppPrefrence.getInstance().getPin(), 0);
                                            FlowOrganizer.getInstance().add(fragment, false);*/
                                        } else if (data.getResultDescription() == null || data.getResultDescription().trim().equalsIgnoreCase("NA") || data.getResultDescription().trim().equalsIgnoreCase("")) {
                                           /* baseFragment.showResponseDialog("Login", data.getResultNamespace(),
                                                    String.valueOf(data.getTID()));*/
                                        } else {
                                            /*baseFragment.showResponseDialog(" Login", data.getResultDescription().trim(), String.valueOf(data.getTID()));*/
                                        }
                                    } else if (data.getStatus().contains("success")) {
                                        AppPrefrence.getInstance().setAppKey(data.getAppKey());
                                        AppPrefrence.getInstance().setTypeId(data.getTypeId());
                                        //AppPrefrence.getInstance().setInitiator(number);
                                        AppPrefrence.getInstance().setName(data.getName());
                                        AppPrefrence.getInstance().setRating(data.getRating());
                                        AppPrefrence.getInstance().setsessionId(data.getSessionId());
                                        AppPrefrence.getInstance().setLocation(data.getLocation());
                                        AppPrefrence.getInstance().setEmail(data.getEmail());
                                        // AppPrefrence.getInstance().setPin(password);
                                        AppSingle.getInstance().setSuccessLogin(true);
                                        AppPrefrence.getInstance().setActivityExecuted(true);
                                        AppSingle.getInstance().setUserName(data.getName());
                                        AppSingle.getInstance().setUserEmail(data.getEmail());
                                        Intent intent = new Intent(Splash.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        /*baseFragment.showResponseDialog("Login", data.getResultNamespace(),String.valueOf(data.getTID()));*/
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Exception in Login :: ", e.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelBase> call, Throwable e) {
                            //baseFragment.dismissProgressDialog();
                            /*if (e instanceof SocketTimeoutException)
                               // baseFragment.showResponseDialog("Other Bill Verify", "SERVER TIMEOUT");
                            else
                                baseFragment.showResponseDialog("Other Bill Verify", "SERVER GOT ERROR");*/
                            Log.e("onError", "yes ");
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }

    private class IntentLauncher extends Thread {

        public void run() {
            try {
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
            }
            Intent intent = new Intent(Splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
