
package com.company.networkcalls.LogInPackage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LogInRequestModel {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("fingerPrintKey")
    @Expose
    private String fingerPrintKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFingerPrintKey() {
        return fingerPrintKey;
    }

    public void setFingerPrintKey(String fingerPrintKey) {
        this.fingerPrintKey = fingerPrintKey;
    }

}
