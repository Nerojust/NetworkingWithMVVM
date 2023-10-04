package com.company.networkcalls.Views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivityBinding <VB extends ViewBinding> extends AppCompatActivity {
    
    protected VB myBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        executeViewBinding();
        inItComponent();
        getViewBinding();
    }

    protected abstract void inItComponent();

    public VB getViewBinding() {
     return myBinding;   
    }

   

    private void executeViewBinding() {
        myBinding = inflateBinding();
        setContentView(myBinding.getRoot());
    }

    protected abstract VB inflateBinding();

    protected abstract int getLayoutResource();
}