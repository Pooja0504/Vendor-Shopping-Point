package com.shopping_point.vendor_shopping_point.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProduct {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("ShortDesc")
    @Expose
    private String ShortDesc;
    @SerializedName("Rating")
    @Expose
    private String Rating;
    @SerializedName("Price")
    @Expose
    private String Price;

    public String getImage() {
        return image;
    }


    public String getTitle() {
        return Title;
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



}