package com.company.networkcalls.ViewModels;

import static com.company.networkcalls.ApiClient.RetrofitClient.getNetworkService;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.networkcalls.ApiClient.RetrofitClient;
import com.company.networkcalls.SignUpPackage.SignUpRequestModel;
import com.company.networkcalls.SignUpPackage.SignUpResponseModel;
import com.company.networkcalls.models.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = "RegistrationActivity";

    private final MutableLiveData<Resource<SignUpResponseModel>> signUpLivedata;

    public SignUpViewModel() {
        this.signUpLivedata = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<SignUpResponseModel>> getSignUpLivedata() {
        return signUpLivedata;
    }

    public void executeSignUpRequest(String userFirstName, String userLastName, String userPhoneNumber,
                                     String userUsername, String userPassword, String userFingerPrint) {

        SignUpRequestModel mySignUpRequestModel = new SignUpRequestModel();
        mySignUpRequestModel.setFirstName(userFirstName);
        mySignUpRequestModel.setLastName(userLastName);
        mySignUpRequestModel.setUsername(userUsername);
        mySignUpRequestModel.setPhoneNumber(userPhoneNumber);
        mySignUpRequestModel.setFingerPrintKey(userFingerPrint);
        mySignUpRequestModel.setPassword(userPassword);

        Log.d(TAG, "createPostRequest: " + mySignUpRequestModel);

        Call<SignUpResponseModel> myCall = getNetworkService().getSignUpResponse(mySignUpRequestModel);

        signUpLivedata.postValue(Resource.loading());

        myCall.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponseModel> call, @NonNull Response<SignUpResponseModel> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    signUpLivedata.postValue(Resource.success(response.body()));
                } else {
                    String errorMessage = "";
                    errorMessage = response.message();

                    signUpLivedata.postValue(Resource.error(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponseModel> call, @NonNull Throwable t) {
                signUpLivedata.postValue(Resource.error(t.getMessage()));
            }
        });


    }


}
