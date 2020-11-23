package com.shopping_point.vendor_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBannersApiResponse {

    @SerializedName("posters")
    private List<GetBanners> posters;

    public List<GetBanners> getBanners() {
        return posters;
    }

    public void setPosters(List<GetBanners> posters) {
        this.posters = posters;
    }
}
