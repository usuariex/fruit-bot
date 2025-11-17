package com.devs.frutybot.data.dto;

public class RequestItemDto {
    private String requestId;
    private String status; // "Procesando", "Listo", "Error"
    private String fruta;
    private String departamento;
    private String photoPath; // opcional, para mostrar miniatura

    public RequestItemDto(String requestId, String status, String photoPath) {
        this.requestId = requestId;
        this.status = status;
        this.photoPath = photoPath;
    }

    public String getRequestId() { return requestId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFruta() { return fruta; }
    public void setFruta(String fruta) { this.fruta = fruta; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getPhotoPath() { return photoPath; }
}
