package com.jsparser.model;

public class Outcome {
    private String name;
    private double price;
    private long id;

    public Outcome() {}

    public Outcome(String name, double price, long id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }
}
