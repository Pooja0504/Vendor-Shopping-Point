package com.shopping_point.vendor_shopping_point.model;

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



    public String getImage() {
        return image;
    }



}
