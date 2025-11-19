package com.devs.frutybot.data.dto;

import java.io.Serializable;
import java.util.Objects;

public class RequestItemDto implements Serializable {
    private String requestId;
    private String status; // "Procesando", "Listo", "Error"
    private FruitDto fruit; // objeto completo del backend
    private String photoPath; // opcional, para mostrar miniatura

    public RequestItemDto() { }

    // Constructor para la primera respuesta (sin fruit)
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

    // Getters / Setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public FruitDto getFruit() { return fruit; }
    public void setFruit(FruitDto fruit) { this.fruit = fruit; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestItemDto)) return false;
        RequestItemDto that = (RequestItemDto) o;
        return Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

    @Override
    public String toString() {
        return "RequestItemDto{" +
                "requestId='" + requestId + '\'' +
                ", status='" + status + '\'' +
                ", fruit=" + (fruit != null ? "present" : "null") +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
