package com.shopping_point.vendor_shopping_point.model;

public class Upload {


    private String image;
    private  int vendorId;

    public Upload(String encode_image, int vendorId) {

        this.image = encode_image;
        this.vendorId = vendorId;



    }

    public int getVendorId() {
        return vendorId;
    }

    public String getImage() {
        return image;
    }


}