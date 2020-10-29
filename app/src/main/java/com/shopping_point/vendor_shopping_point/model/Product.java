package com.shopping_point.vendor_shopping_point.model;

public class Product {
    private int seller_id;
    private String product_name;
    private String price;
    private String description;
    private String category;
    private String brand;
    private String quantity;

    private String image;


    public Product(int seller_id, String product_name, String price, String description, String category,  String brand,String quantity,String image) {
        this.seller_id=seller_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.brand = brand;
        this.image = image;


    }


    public String getProduct_name() {
        return product_name;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public int getSeller_id() {
        return seller_id;
    }
}