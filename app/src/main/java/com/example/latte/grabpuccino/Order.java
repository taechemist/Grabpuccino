package com.example.latte.grabpuccino;

/**
 * Created by latte on 6/19/17.
 */

public class Order {
    private String menuId;
    private String vendorId;
    private String status;

    public Order() {
    }

    public Order(String menuId, String vendorId, String status) {
        this.menuId = menuId;
        this.vendorId = vendorId;
        this.status = status;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "menuId='" + menuId + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
