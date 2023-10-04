package com.company.networkcalls.ViewModels;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.networkcalls.ApiClient.RetrofitClient;
import com.company.networkcalls.SignUpPackage.SignUpRequestModel;
import com.company.networkcalls.SignUpPackage.SignUpResponseModel;

import java.io.Closeable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = "RegistrationActivity";

    private MutableLiveData<SignUpResponseModel> signUpLivedata;

    public SignUpViewModel() {
        this.signUpLivedata = new MutableLiveData<>();
    }

    public MutableLiveData<SignUpResponseModel> getSignUpLivedata(){
        return signUpLivedata;
    }

    public void getSignUpDetails(String userFirstName, String userLastName, String userPhoneNumber, String userUsername, String userPassword, String userFingerPrint){

        SignUpRequestModel mySignUpRequestModel = new SignUpRequestModel();
        mySignUpRequestModel.setFirstName(userFirstName);
        mySignUpRequestModel.setLastName(userLastName);
        mySignUpRequestModel.setUsername(userUsername);
        mySignUpRequestModel.setPhoneNumber(userPhoneNumber);
        mySignUpRequestModel.setFingerPrintKey(userFingerPrint);
        mySignUpRequestModel.setPassword(userPassword);
        Log.d(TAG, "createPostRequest: " + mySignUpRequestModel);

        Call<SignUpResponseModel> myCall = RetrofitClient.getNetworkService().getSignUpResponse(mySignUpRequestModel);

        myCall.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(Call<SignUpResponseModel> call, Response<SignUpResponseModel> response) {

                if(response.isSuccessful()){

                 signUpLivedata.postValue(response.body());
                }
                else{
                    signUpLivedata.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {

                signUpLivedata.postValue(null);

            }
        });


    }






}
