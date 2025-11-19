package com.devs.frutybot.data.dto;

import java.util.List;

public class FruitDto {
    private String nombre;
    private String pais;
    private String departamento;
    private String descripcion;
    private String procesoDeMaduracion;
    private String informacionNutricional;
    private String calorias;
    private List<String> vitaminas;
    private String fibra;
    private String azucares;
    private String temporada;
    private String tipo;
    private String valida;

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getProcesoDeMaduracion() { return procesoDeMaduracion; }
    public void setProcesoDeMaduracion(String procesoDeMaduracion) { this.procesoDeMaduracion = procesoDeMaduracion; }

    public String getInformacionNutricional() { return informacionNutricional; }
    public void setInformacionNutricional(String informacionNutricional) { this.informacionNutricional = informacionNutricional; }

    public String getCalorias() { return calorias; }
    public void setCalorias(String calorias) { this.calorias = calorias; }

    public List<String> getVitaminas() { return vitaminas; }
    public void setVitaminas(List<String> vitaminas) { this.vitaminas = vitaminas; }

    public String getFibra() { return fibra; }
    public void setFibra(String fibra) { this.fibra = fibra; }

    public String getAzucares() { return azucares; }
    public void setAzucares(String azucares) { this.azucares = azucares; }

    public String getTemporada() { return temporada; }
    public void setTemporada(String temporada) { this.temporada = temporada; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getValida() { return valida; }
    public void setValida(String valida) { this.valida = valida; }


}