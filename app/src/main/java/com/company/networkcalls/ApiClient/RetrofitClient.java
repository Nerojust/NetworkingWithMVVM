package com.company.networkcalls.ApiClient;

import com.company.networkcalls.WebService.NetworkService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://8154-102-89-22-39.ngrok-free.app";
    static Retrofit myRetrofit;
    static NetworkService myNetworkService;

    public static Retrofit getRetrofit() {

        HttpLoggingInterceptor myHttpLoggingInterceptor = new HttpLoggingInterceptor();
        myHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(myHttpLoggingInterceptor).build();
        if (myRetrofit == null) {
            myRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(myClient).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return myRetrofit;
    }

    public static NetworkService getNetworkService() {
        return getRetrofit().create(NetworkService.class);
    }
}
