package com.devs.frutybot.domain.model;

public class Fruit {
    private int id;
    private String nombre;
    private String imagen;
    private String descripcion;

    public Fruit(int id, String nombre, String imagen, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getImagen() { return imagen; }
    public String getDescripcion() { return descripcion; }
}
