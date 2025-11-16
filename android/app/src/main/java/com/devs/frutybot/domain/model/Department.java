package com.devs.frutybot.domain.model;

public class Department {
    private String name;
    private String imageUrl;

    public Department(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
}
