package com.shopping_point.vendor_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyProductResponse {

    @SerializedName("MyProduct")
    private List<MyProduct> MyProduct;

    public List<MyProduct> getMyProduct() {
        return MyProduct;
    }


}
