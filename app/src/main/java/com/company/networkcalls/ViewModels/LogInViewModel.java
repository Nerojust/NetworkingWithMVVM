package com.company.networkcalls.ViewModels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.networkcalls.ApiClient.RetrofitClient;
import com.company.networkcalls.LogInPackage.LogInRequestModel;
import com.company.networkcalls.LogInPackage.LogInResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInViewModel extends ViewModel {

    private static final String TAG = "LogInViewModel";

    private MutableLiveData<LogInResponseModel> logInResponseLiveData;

    public LogInViewModel(){
        this.logInResponseLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<LogInResponseModel> getLogInResponseLiveData(){
        return logInResponseLiveData;
    }

    public void existingUser(String username, String fingerPrint , String password){

        LogInRequestModel myLogInRequest = new LogInRequestModel();
        myLogInRequest.setUsername(username);
        myLogInRequest.setFingerPrintKey(fingerPrint);
        myLogInRequest.setPassword(password);

        Log.d(TAG, "Existing User Verification" + myLogInRequest);


        Call<LogInResponseModel> myCall = RetrofitClient.getNetworkService().getLogInResponse(myLogInRequest);
        myCall.enqueue(new Callback<LogInResponseModel>() {
            @Override
            public void onResponse(Call<LogInResponseModel> call, Response<LogInResponseModel> response) {

                if(response.isSuccessful() & response.body() != null){

                    logInResponseLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<LogInResponseModel> call, Throwable t) {

                logInResponseLiveData.postValue(null);

            }
        });

    }
}
