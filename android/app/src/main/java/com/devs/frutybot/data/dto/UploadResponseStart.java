package com.devs.frutybot.data.dto;

public class UploadResponseStart {
    private String message;
    private String requestId;
    private String status;
    private String imageUrl;


    public String getMessage() { return message; }
    public String getRequestId() { return requestId; }
    public String getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }


    public void setMessage(String message) { this.message = message; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public void setStatus(String status) { this.status = status; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
