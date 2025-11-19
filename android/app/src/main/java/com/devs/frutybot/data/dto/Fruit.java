package com.devs.frutybot.data.dto;



import com.google.gson.annotations.SerializedName;

public class Fruit {
    @SerializedName("FRUTA_ID")
    private int id;

    @SerializedName("NOMBRE")
    private String nombre;

    @SerializedName("URL_IMG")
    private String imagen;

    @SerializedName("DESCRIPCION")
    private String descripcion;

    @SerializedName("departamento_nombre")
    private String departamentoNombre;

    @SerializedName("departamento_descripcion")
    private String departamentoDescripcion;

    // getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getImagen() { return imagen; }
    public String getDescripcion() { return descripcion; }
    public String getDepartamentoNombre() { return departamentoNombre; }
    public String getDepartamentoDescripcion() { return departamentoDescripcion; }
}
