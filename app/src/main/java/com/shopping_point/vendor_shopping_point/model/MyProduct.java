package com.shopping_point.vendor_shopping_point.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProduct {



    @SerializedName("image")
    private String image;
    @SerializedName("Title")
    private String Title;
    @SerializedName("ShortDesc")
    private String ShortDesc;
    @SerializedName("Rating")
    private String Rating;
    @SerializedName("Price")
    private String Price;
    @SerializedName("isActive")
    @Expose
    private String isActive;
    @SerializedName("product_id")
    private int product_id;

    public String getTitle() {
        return Title;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getShortDesc() {
        return ShortDesc;
    }



    public String getRating() {
        return Rating;
    }



    public String getPrice() {
        return Price;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getImage() {
        return image;
    }



}
