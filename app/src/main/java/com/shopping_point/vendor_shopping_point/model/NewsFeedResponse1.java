package com.shopping_point.vendor_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsFeedResponse1 {

    @SerializedName("posters")
    private List<NewsFeed1> posters;

    public List<NewsFeed1> getPosters1() {
        return posters;
    }

    public void setPosters(List<NewsFeed1> posters) {
        this.posters = posters;
    }
}
