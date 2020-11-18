package com.shopping_point.vendor_shopping_point.model;

public class Banner {


    private String image;
    private  int vendor_id;

    public Banner(String image, int vendor_id) {

        this.image = image;
        this.vendor_id=vendor_id;

    }

    public int getVendor_id() {
        return vendor_id;
    }

    public String getImage() {
        return image;
    }


}