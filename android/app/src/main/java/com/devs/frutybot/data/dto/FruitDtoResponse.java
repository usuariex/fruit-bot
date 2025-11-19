package com.devs.frutybot.data.dto;

import com.google.gson.annotations.SerializedName;

public class FruitDtoResponse {

    private String id;
    private String status;

    @SerializedName("fruit")
    private FruitDto fruit;

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public FruitDto getFruit() { return fruit; }
    public void setFruit(FruitDto fruit) { this.fruit = fruit; }
}
