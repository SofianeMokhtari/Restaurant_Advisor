package com.example.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Menu {

    private String id;
    private String id_user;
    private String id_restau;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("prix")
    @Expose
    private String prix;

    public Menu(String name, String description, String prix) {
        this.name = name;
        this.description = description;
        this.prix = prix;

    }
    public String getPrix() {
        return prix;
    }

    public void setPrix(String localisation) {
        this.prix = localisation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getId_restau() {
        return id_restau;
    }

    public void setId_restau(String id_restau) {
        this.id_restau = id_restau;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}