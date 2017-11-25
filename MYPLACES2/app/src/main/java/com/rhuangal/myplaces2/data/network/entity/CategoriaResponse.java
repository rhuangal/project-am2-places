package com.rhuangal.myplaces2.data.network.entity;

/**
 * Created by rober on 24-Nov-17.
 */

public class CategoriaResponse {

    private int id_cat;
    private String nom_cat;
    private String ico_cat;
    private String des_cat;

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }

    public String getIco_cat() {
        return ico_cat;
    }

    public void setIco_cat(String ico_cat) {
        this.ico_cat = ico_cat;
    }

    public String getDes_cat() {
        return des_cat;
    }

    public void setDes_cat(String des_cat) {
        this.des_cat = des_cat;
    }
}
