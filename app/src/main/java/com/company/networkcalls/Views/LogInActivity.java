package com.company.networkcalls.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.company.networkcalls.ApiClient.RetrofitClient;
import com.company.networkcalls.LogInPackage.LogInRequestModel;
import com.company.networkcalls.LogInPackage.LogInResponseModel;
import com.company.networkcalls.R;
import com.company.networkcalls.Utilities.AppConstant;
import com.company.networkcalls.ViewModels.LogInViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {



    private EditText  usernameEditText, passwordEditText, fingerPrint;
    private Button logInButton;

    private ProgressBar myProgressBar;

SharedPreferences mySharedPreferences;

    LogInViewModel myLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inItView();
        persistData();
        inItModel();
        inItListener();
        textWatcher();
    }

    private void persistData() {

        mySharedPreferences = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        String userName =    mySharedPreferences.getString(AppConstant.USERNAME, "");

     usernameEditText.setText(userName);


    }


    public void inItView() {

        usernameEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);
        fingerPrint = findViewById(R.id.et_finger_print);
        logInButton = findViewById(R.id.btn_log_in);
        myProgressBar = findViewById(R.id.progress_bar);

    }

    private void inItListener() {

        logInButton();

    }


    private void logInButton() {
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String    user_name = usernameEditText.getText().toString().trim();
            String    finger_Print = fingerPrint.getText().toString().trim();
            String    pass_word = passwordEditText.getText().toString().trim();

                myLoginViewModel.existingUser(user_name, finger_Print, pass_word);

//                logInUser(userUsername, userFingerPrint, userPassword);

            }
        });
    }

    private void inItModel() {

        myLoginViewModel = ViewModelProviders.of(this).get(LogInViewModel.class);
        myLoginViewModel.getLogInResponseLiveData().observe(this, new Observer<LogInResponseModel>() {
            @Override
            public void onChanged(LogInResponseModel logInResponseModel) {

                if(logInResponseModel != null){

                    if(validateUsername() & validatePassword()){

                        myProgressBar.setVisibility(View.VISIBLE);


                        String successMessage = logInResponseModel.getMessage();

                        Toast.makeText(LogInActivity.this, successMessage, Toast.LENGTH_LONG).show();

                        Intent myIntent = new Intent(LogInActivity.this, DashBoardActivity.class);
                        startActivity(myIntent);


                    }
                }

                else{

                    myProgressBar.setVisibility(View.GONE);

                    Toast.makeText(LogInActivity.this, "failed to log in", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void textWatcher() {
        usernameEditText.addTextChangedListener(myTextWatcher);
        fingerPrint.addTextChangedListener(myTextWatcher);
        passwordEditText.addTextChangedListener(myTextWatcher);
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String    userUsername = usernameEditText.getText().toString().trim();
            String   userFingerPrint = fingerPrint.getText().toString().trim();
            String   userPassword = passwordEditText.getText().toString().trim();



            logInButton.setEnabled(!userUsername.isEmpty() && userFingerPrint.isEmpty() && !userPassword.isEmpty());



        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private boolean validateUsername() {
        SharedPreferences mySharedPreference = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        String loginUsername = mySharedPreference.getString(AppConstant.USERNAME, " ");
        String retrievalUsername = usernameEditText.getText().toString().trim();


        if (retrievalUsername.isEmpty()) {
            usernameEditText.setError("Can't be empty");
            return false;
        }
        else if (!retrievalUsername.equals(loginUsername)) {
            usernameEditText.setError("Invalid Username");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String retrievalPassword = passwordEditText.getText().toString().trim();
        SharedPreferences mySharedPreference = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        String loginPassword = mySharedPreference.getString(AppConstant.PASSWORD, " ");


        if (retrievalPassword.isEmpty()) {
            passwordEditText.setError("Can't be empty");
            return false;
        } else if (!retrievalPassword.equals(loginPassword)) {
            passwordEditText.setError("Invalid Password");
            return false;
        }
        return true;
    }

}


