package com.spania.sala17.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bebida {
    @SerializedName("id_bebida")
    @Expose
    private long id_bebida;
    @SerializedName("nombreBebida")
    @Expose
    private String nombreBebida;
    @SerializedName("precio")
    @Expose
    private int precio;

    @SerializedName("imgBebida")
    @Expose
    private String imgBebida;

    public long getId_bebida() {
        return id_bebida;
    }

    public void setId_bebida(long id_bebida) {
        this.id_bebida = id_bebida;
    }

    public String getNombreBebida() {
        return nombreBebida;
    }

    public void setNombreBebida(String nombreBebida) {
        this.nombreBebida = nombreBebida;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getImgBebida() {
        return imgBebida;
    }

    public void setImgBebida(String imgBebida) {
        this.imgBebida = imgBebida;
    }

    public Bebida(long id_bebida, String nombreBebida, int precio, String imgBebida) {
        this.id_bebida = id_bebida;
        this.nombreBebida = nombreBebida;
        this.precio = precio;
        this.imgBebida = imgBebida;
    }
}
