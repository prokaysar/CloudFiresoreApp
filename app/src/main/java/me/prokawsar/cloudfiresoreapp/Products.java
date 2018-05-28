package me.prokawsar.cloudfiresoreapp;

public class Products {
    private String name,brand,desc;
    private double price;
    private int qty;

    public Products() {
    }

    public Products(String name, String brand, String desc, double price, int qty) {
        this.name = name;
        this.brand = brand;
        this.desc = desc;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public String toString() {
        return name +"\n"+brand+"\n"+desc+"\n"+price+"\n"+qty;
    }
}
