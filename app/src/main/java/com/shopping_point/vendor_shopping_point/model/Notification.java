package com.shopping_point.vendor_shopping_point.model;

import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("image")
    private String image;
    @SerializedName("Title")
    private String Title;

    @SerializedName("body")
    private String body;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
