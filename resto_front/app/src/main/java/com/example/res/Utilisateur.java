package com.example.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Utilisateur {
    @SerializedName("access_token")
    @Expose
    private String token;
    public Utilisateur(String token) {
        this.token = "Bearer " + token;
    }
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
}