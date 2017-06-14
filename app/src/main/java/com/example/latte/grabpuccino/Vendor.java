package com.example.latte.grabpuccino;

/**
 * Created by latte on 6/13/17.
 */

public class Vendor {
    private String name;
    private String icon;
    private String details;

    public Vendor (){
    }

    public Vendor(String name, String icon, String details) {
        this.name = name;
        this.icon = icon;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}

