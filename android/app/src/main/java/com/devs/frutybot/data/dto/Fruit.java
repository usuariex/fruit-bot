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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDepartamentoNombre() {
        return departamentoNombre;
    }

    public void setDepartamentoNombre(String departamentoNombre) {
        this.departamentoNombre = departamentoNombre;
    }

    public String getDepartamentoDescripcion() {
        return departamentoDescripcion;
    }

    public void setDepartamentoDescripcion(String departamentoDescripcion) {
        this.departamentoDescripcion = departamentoDescripcion;
    }
}
