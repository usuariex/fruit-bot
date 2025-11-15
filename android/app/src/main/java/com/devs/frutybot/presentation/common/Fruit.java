package com.devs.frutybot.presentation.common;

public class Fruit {

    /*
    private final String id;
    private final String name;
    private final String color;
    private final double weight;
    private final String origin;

    public Fruit(String id, String name, String color, double weight, String origin) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.origin = origin;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public double getWeight() { return weight; }
    public String getOrigin() { return origin; }*/

    private String name;
    private String color;
    private int price;

    public Fruit(String name, String color, int price) {
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public String getName() { return name; }
    public String getColor() { return color; }
    public int getPrice() { return price; }
}
