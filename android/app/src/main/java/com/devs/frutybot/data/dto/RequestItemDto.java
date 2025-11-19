package com.devs.frutybot.data.dto;

public class RequestItemDto {
    private String requestId;
    private String status; // "Procesando", "Listo", "Error"
    private FruitDto fruit; // objeto completo del backend
    private String photoPath; // opcional, para mostrar miniatura

    // Constructor para la primera respuesta
    public RequestItemDto(String requestId, String status, String photoPath) {
        this.requestId = requestId;
        this.status = status;
        this.photoPath = photoPath;
    }

    // Constructor opcional para cuando ya tienes el objeto FruitDto
    public RequestItemDto(String requestId, String status, String photoPath, FruitDto fruit) {
        this.requestId = requestId;
        this.status = status;
        this.photoPath = photoPath;
        this.fruit = fruit;
    }

    public String getRequestId() { return requestId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public FruitDto getFruit() { return fruit; }
    public void setFruit(FruitDto fruit) { this.fruit = fruit; }

    public String getPhotoPath() { return photoPath; }
}
