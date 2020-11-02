package com.shopping_point.vendor_shopping_point.model;

public class AddBannerApiResponse {

    private boolean error;
    private String message;


    public AddBannerApiResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }


    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }


}
