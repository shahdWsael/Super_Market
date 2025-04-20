package com.example.myapplication;

public class Product {
    private String name;
    private String imageName;
    private double price;
    private int quantity;

    public Product(String name, String imageName, double price, int quantity) {
        this.name = name;
        this.imageName = imageName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
