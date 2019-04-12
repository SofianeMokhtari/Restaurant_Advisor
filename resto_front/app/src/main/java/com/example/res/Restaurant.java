package com.example.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    private String id;
    private String id_user;
    private String note;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("localisation")
    @Expose
    private String localisation;
    @SerializedName("téléphone")
    @Expose
    private String telephone;
    @SerializedName("web_site")
    @Expose
    private String web_site;
    @SerializedName("horaire_semaine")
    @Expose
    private String horaire_semaine;
    @SerializedName("horaire_week")
    @Expose
    private String horaire_week;

    public Restaurant(String name, String description, String localisation, String telephone, String web_site, String horaire_semaine, String horaire_week) {
        this.name = name;
        this.description = description;
        this.localisation = localisation;
        this.telephone = telephone;
        this.web_site = web_site;
        this.horaire_semaine = horaire_semaine;
        this.horaire_week = horaire_week;
    }

    public String getHoraire_week() {
        return horaire_week;
    }

    public void setHoraire_week(String horaire_week) {
        this.horaire_week = horaire_week;
    }

    public String getHoraire_semaine() {
        return horaire_semaine;
    }

    public void setHoraire_semaine(String horaire_semaine) {
        this.horaire_semaine = horaire_semaine;
    }

    public String getWeb_site() {
        return web_site;
    }

    public void setWeb_site(String web_site) {
        this.web_site = web_site;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
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

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
