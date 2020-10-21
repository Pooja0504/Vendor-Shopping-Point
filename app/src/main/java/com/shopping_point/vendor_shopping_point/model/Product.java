package com.shopping_point.vendor_shopping_point.model;

public class Product {
    private String product_name;
    private String price;
    private String description;
    private String category;



    public Product(String product_name, String price, String description, String category) {
        this.product_name = product_name;
        this.price = price;
        this.description=description;
        this.category = category;

    }


    public String getProduct_name() {
        return product_name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory(){return category;}



}