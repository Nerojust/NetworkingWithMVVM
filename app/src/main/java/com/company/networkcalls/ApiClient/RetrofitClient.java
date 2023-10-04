package com.company.networkcalls.ApiClient;

import com.company.networkcalls.WebService.NetworkService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://30a6-102-89-33-39.ngrok-free.app";

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor myHttpLoggingInterceptor = new HttpLoggingInterceptor();
        myHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(myHttpLoggingInterceptor).build();

        Retrofit myRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(myClient).
                addConverterFactory(GsonConverterFactory.create()).build();

        return myRetrofit;
    }

    public static NetworkService getNetworkService(){

        NetworkService myNetworkService = getRetrofit().create(NetworkService.class);

        return myNetworkService;
    }





}
