package com.zerosymbol.directorylisting.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zerosymbol.directorylisting.constants.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 21-04-2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            //Print log on Logcat
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(AppConstants.IAppPref.REQUEST_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(AppConstants.IAppPref.REQUEST_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getAPIServicePhp(String baseUrl) {

        if (retrofit2 == null) {
            //Print log on Logcat
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(AppConstants.IAppPref.REQUEST_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(AppConstants.IAppPref.REQUEST_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit2 = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit2;
    }
    public static Retrofit getClient2(String baseUrl) {
        if (retrofit == null) {
            //Print log on Logcat
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(AppConstants.IAppPref.REQUEST_TIME_OUT2, TimeUnit.SECONDS)
                    .connectTimeout(AppConstants.IAppPref.REQUEST_TIME_OUT2, TimeUnit.SECONDS)
                    .addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
