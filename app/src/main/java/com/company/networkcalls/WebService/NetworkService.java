package com.company.networkcalls.WebService;

import com.company.networkcalls.LogInPackage.LogInRequestModel;
import com.company.networkcalls.LogInPackage.LogInResponseModel;
import com.company.networkcalls.SignUpPackage.SignUpRequestModel;
import com.company.networkcalls.SignUpPackage.SignUpResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {

    @POST("api/v1/users/register")
    Call<SignUpResponseModel> getSignUpResponse(@Body SignUpRequestModel mySignUpRequestModel);

    @POST("api/v1/users/login")
    Call<LogInResponseModel> getLogInResponse(@Body LogInRequestModel myLogInRequestModel);
}
