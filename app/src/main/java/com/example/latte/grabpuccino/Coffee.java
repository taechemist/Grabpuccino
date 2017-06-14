package com.example.latte.grabpuccino;

/**
 * Created by latte on 6/14/17.
 */

public class Coffee {
    private String name;
    private double price;
    private String icon;

    public Coffee() {
    }

    public Coffee(String name, double price, String icon) {
        this.name = name;
        this.price = price;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", icon='" + icon + '\'' +
                '}';
    }
}
