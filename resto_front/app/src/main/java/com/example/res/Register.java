package com.example.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("prénom")
    @Expose
    private String prenom;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password_conf")
    @Expose
    private String password_conf;

    public Register(String username, String nom, String prenom, String email, String age, String telephone, String password, String password_conf) {
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
        this.telephone = telephone;
        this.password = password;
        this.password_conf = password_conf;
    }

    public String getUsername() {return username;}
    public String getNom() {return nom;}
    public String getPrenom() {return prenom;}
    public String getEmail() {return email;}
    public String getAge() {return age;}
    public String getTelephone() {return telephone;}
    public String getPassword() {return password;}
    public String getPassword_conf() {return password_conf;}

    public void setUsername(String username) {this.username = username;}
    public void setNom(String nom) {this.nom = nom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public void setEmail(String email) {this.email = email;}
    public void setAge(String age) {this.age = age;}
    public void setTelephone(String telephone) {this.telephone = telephone;}
    public void setPassword(String password) {this.password = password;}
    public void setPassword_conf(String password_conf) {this.password_conf = password_conf;}
}
