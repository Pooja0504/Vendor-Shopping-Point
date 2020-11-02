package com.shopping_point.vendor_shopping_point.model;

public class UploadPhoto {


    private String image;
    private int id;


    public UploadPhoto(String image, int id) {

        this.image = image;
        this.id = id;


    }




    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
}