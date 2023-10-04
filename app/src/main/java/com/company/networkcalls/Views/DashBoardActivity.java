package com.company.networkcalls.Views;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.viewbinding.ViewBinding;

import com.company.networkcalls.R;
import com.company.networkcalls.Utilities.AppConstant;
import com.company.networkcalls.Views.BaseActivityBinding;
import com.company.networkcalls.databinding.ActivityDashboardBinding;

public class DashBoardActivity extends BaseActivityBinding<ActivityDashboardBinding> {

SharedPreferences mySharedPreferences;


    @Override
    protected ActivityDashboardBinding inflateBinding() {
     return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void inItComponent() {
        persistData();



    }

    private void persistData() {

        mySharedPreferences = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        String firstName = mySharedPreferences.getString(AppConstant.FIRST_NAME, "");
        String lastName = mySharedPreferences.getString(AppConstant.LAST_NAME, "");
        String phoneNumber = mySharedPreferences.getString(AppConstant.PHONE, "");

        getViewBinding().tvFirstName.setText(firstName);
        getViewBinding().tvLastName.setText(lastName);
        getViewBinding().tvPhoneNumber.setText(phoneNumber);
    }




}