package com.company.networkcalls.Views;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.company.networkcalls.R;
import com.company.networkcalls.SignUpPackage.SignUpResponseModel;
import com.company.networkcalls.Utilities.AppConstant;
import com.company.networkcalls.ViewModels.SignUpViewModel;
import com.company.networkcalls.models.Resource;

public class SignUpActivity extends AppCompatActivity {
//    private static final String TAG = "RegistrationActivity";

    private EditText firstNameEditText, lastNameEditText, phoneNumberEditText, usernameEditText, passwordEditText,
            fingerPrintEditText;
    private Button signUpButton;


    private ProgressBar myProgressBar;

//    String userFirstName, userLastName, userPhoneNumber, userUsername, userFingerPrint, userPassword;

    String appFirstName, appLastName, appPhoneNumber;

    SharedPreferences mySharedPreference;
    SharedPreferences.Editor myEditor;

    SignUpViewModel mySignUpViewModel;
    SignUpResponseModel signUpResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        inItView();
        initSubscribers();
        inItListener();
        textListener();

    }


    public void inItView() {
        firstNameEditText = findViewById(R.id.et_first_name);
        lastNameEditText = findViewById(R.id.et_last_name);
        phoneNumberEditText = findViewById(R.id.et_phone);
        usernameEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);
        fingerPrintEditText = findViewById(R.id.et_finger_print);
        signUpButton = findViewById(R.id.btn_sign_up);
        myProgressBar = findViewById(R.id.progress_bar);

        mySignUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        mySharedPreference = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        myEditor = mySharedPreference.edit();
        myEditor.putString(AppConstant.FIRST_NAME, appFirstName);
        myEditor.putString(AppConstant.LAST_NAME, appLastName);
        myEditor.putString(AppConstant.PHONE, appPhoneNumber);
        myEditor.apply();
    }

    private void inItListener() {
        signUpButton();
    }

    private void signUpButton() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userFirstName = firstNameEditText.getText().toString().trim();
                String userLastName = lastNameEditText.getText().toString().trim();
                String userPhoneNumber = phoneNumberEditText.getText().toString().trim();
                String userUsername = usernameEditText.getText().toString().trim();
                String userFingerPrint = fingerPrintEditText.getText().toString().trim();
                String userPassword = passwordEditText.getText().toString().trim();


                if (validateFirstName() & validatePassword() & validateLastName() & validatePhoneNumber() & validateUsername() & validatePassword()) {
                    mySignUpViewModel.executeSignUpRequest(userFirstName, userLastName, userPhoneNumber, userPassword, userFingerPrint, userUsername);
                    myProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initSubscribers() {
        mySignUpViewModel.getSignUpLivedata().observe(this, this::processSignUpResponse);
    }

    private void processSignUpResponse(Resource<SignUpResponseModel> signUpResponseModel) {
        switch (signUpResponseModel.status) {
            case SUCCESS:
                myProgressBar.setVisibility(View.GONE);

                if (signUpResponseModel.data != null) {
                    appFirstName = signUpResponseModel.data.getResult().getFirstName();

                    appLastName = signUpResponseModel.data.getResult().getLastName();
                    appPhoneNumber = signUpResponseModel.data.getResult().getPhoneNumber();

                    String successMessage = signUpResponseModel.data.getMessage();
                    Toast.makeText(SignUpActivity.this, successMessage, Toast.LENGTH_LONG).show();

                    Intent myIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(myIntent);
                }
                break;

            case ERROR:
                myProgressBar.setVisibility(View.GONE);
                sendErrorDialog(signUpResponseModel.message);

                break;
            case LOADING:
                myProgressBar.setVisibility(View.VISIBLE);

                break;
        }
    }

    private void sendErrorDialog(String message) {

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(SignUpActivity.this);
        // inflate view

        View myView = getLayoutInflater().inflate(R.layout.error_dialog, null);
        myBuilder.setView(myView);
        myBuilder.setCancelable(false);

        TextView ok = myView.findViewById(R.id.tv_ok);
        TextView msgView = myView.findViewById(R.id.tv_error_message);
        msgView.setText(message);

        AlertDialog myDialog = myBuilder.create();
        myDialog.show();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog.dismiss();

            }
        });

    }

    public void textListener() {

        firstNameEditText.addTextChangedListener(myTextWatcher);
        lastNameEditText.addTextChangedListener(myTextWatcher);
        phoneNumberEditText.addTextChangedListener(myTextWatcher);
        usernameEditText.addTextChangedListener(myTextWatcher);
        fingerPrintEditText.addTextChangedListener(myTextWatcher);
        passwordEditText.addTextChangedListener(myTextWatcher);
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String firstName = firstNameEditText.getText().toString().trim();
            String lastName = lastNameEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String fingerPrint = fingerPrintEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();


            signUpButton.setEnabled(!firstName.isEmpty() && !lastName.isEmpty() &&
                    !phoneNumber.isEmpty() && !username.isEmpty() && !fingerPrint.isEmpty()
                    && !password.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    };

    private boolean validateFirstName() {
        String retrievalParentName = firstNameEditText.getText().toString().trim();
        String acceptableAlphabets = "^([a-zA-Z]*)$";

        if (retrievalParentName.isEmpty()) {
            firstNameEditText.setError("Can't be empty");
            return false;
        } else if (retrievalParentName.length() < 3) {
            firstNameEditText.setError("Name too short");
            return false;
        } else if (!retrievalParentName.matches(acceptableAlphabets)) {
            firstNameEditText.setError("Must only be in alphabets");
            return false;
        }
        return true;
    }

    private boolean validateLastName() {
        String retrievalChildName = lastNameEditText.getText().toString().trim();
        String acceptableAlphabets = "^([a-zA-Z]*)$";

        if (retrievalChildName.isEmpty()) {
            lastNameEditText.setError("Can't be empty");
            return false;
        } else if (retrievalChildName.length() < 3) {
            lastNameEditText.setError("Name too short");
            return false;
        } else if (!retrievalChildName.matches(acceptableAlphabets)) {
            lastNameEditText.setError("Must only be in alphabets");
            return false;
        }
        return true;
    }

    private boolean validateUsername() {
        String retrievalUsername = usernameEditText.getText().toString().trim();
        String alphaNumericNumbers = "^([a-zA-Z][a-zA-Z$+._\\d]*)$";

        if (retrievalUsername.isEmpty()) {
            usernameEditText.setError("Can't be empty");
            return false;
        } else if (retrievalUsername.length() < 4) {
            usernameEditText.setError("username too short");
            return false;
        } else if (!retrievalUsername.matches(alphaNumericNumbers)) {
            usernameEditText.setError("username too weak, add number");
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber() {
        String retrievalPhoneNumber = phoneNumberEditText.getText().toString().trim();
        String acceptableNumbers = "^([0-9]*)$";

        if (retrievalPhoneNumber.isEmpty()) {
            phoneNumberEditText.setError("Can't be empty");
            return false;
        } else if (retrievalPhoneNumber.length() < 11) {
            phoneNumberEditText.setError("Number typed invalid");
            return false;
        } else if (retrievalPhoneNumber.length() > 11) {
            phoneNumberEditText.setError("Number typed invalid");
            return false;
        } else if (!retrievalPhoneNumber.matches(acceptableNumbers)) {
            phoneNumberEditText.setError("must only be numbers");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {

        String retrievalPassword = passwordEditText.getText().toString().trim();
        String acceptablePassword = "^([a-zA-Z@#?$+._\\d]*)$";

        if (retrievalPassword.isEmpty()) {
            passwordEditText.setError("Can't be empty");
            return false;
        } else if (retrievalPassword.length() < 6) {
            passwordEditText.setError("password too short");
            return false;
        } else if (!retrievalPassword.matches(acceptablePassword)) {
            passwordEditText.setError("too weak, requires letter, number or symbol");
            return false;
        }
        return true;
    }


}


